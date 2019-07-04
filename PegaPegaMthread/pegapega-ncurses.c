// gcc -Wall -o jogo jogo.c -lcurses -lpthread -lm -O2
// Usa <TOKENS> pthreads e 2 mutexes
// ./jogo 1 - Dificuldade Facil
// ./jogo 2 - Dificuldade Media
// ./jogo 3 - Dificuldade Dificil
// Retorna o score final quando acaba o tempo de jogo

#include <curses.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <pthread.h>
#include <math.h>
#include <signal.h>

#define EMPTY ' '
#define CURSOR_PAIR 1
#define TOKEN_PAIR 2
#define EMPTY_PAIR 3
#define LINES 11
#define COLS 11
#define TOKENS 5

enum Dificuldade
{
  EasyTime = 10,
  NormalTime = 7,
  HardTime = 5
};

enum Dificuldade TempoJogo;

int is_move_okay(int y, int x);
void draw_board(void);
void move_token(int token);
void *th_move_token(void *token);
void move_tokens();
void board_refresh(void);
void handle(int sig);

char board[LINES][COLS];
bool tokenlife[TOKENS];
float distances[TOKENS];
int tokenids[TOKENS];

typedef struct CoordStruct
{
  int x;
  int y;
} coord_type;

coord_type cursor, coord_tokens[TOKENS];

pthread_mutex_t mutex_token_reloc;
pthread_mutex_t mutex_token_redraw;
pthread_t threads[TOKENS];

void handle(int sig)
{
  int score = 0;
  for (size_t i = 0; i < TOKENS; i++)
    if (tokenlife[i] == FALSE)
      score++;

  printf("Seu score em %d segundos foi %d TOKENS capturados.", TempoJogo, score);

  for (size_t i = 0; i < TOKENS; i++)
    if (tokenlife[i] == FALSE)
      printf(" Distância do cursor em relação ao TOKEN[%lu] = %f (capturado!).", i, distances[i]);
    else
      printf(" Distância do cursor em relação ao TOKEN[%lu] = %f (não capturado)", i, distances[i]);

  endwin();
  exit(0);
}

int main(int argc, char const *argv[])
{
  int ch;
  srand(time(NULL)); /* inicializa gerador de numeros aleatorios */

  if (argc >= 2)
  {
    const char *a = argv[1];
    int num = atoi(a);

    if (num == 1)
    {
      TempoJogo = EasyTime;
    }
    else if (num == 2)
    {
      TempoJogo = NormalTime;
    }
    else
    {
      TempoJogo = HardTime;
    }
  }
  else
  {
    TempoJogo = EasyTime;
  }

  // printf("\nTempo Escolhido %i\n", TempoJogo);

  signal(SIGALRM, handle);
  alarm(TempoJogo);

  /* inicializa curses */

  initscr();
  keypad(stdscr, TRUE);
  cbreak();
  noecho();

  /* inicializa colors */

  if (has_colors() == FALSE)
  {
    endwin();
    printf("Seu terminal nao suporta cor\n");
    exit(1);
  }

  start_color();

  /* inicializa pares caracter-fundo do caracter */

  init_pair(CURSOR_PAIR, COLOR_YELLOW, COLOR_YELLOW);
  init_pair(TOKEN_PAIR, COLOR_RED, COLOR_RED);
  init_pair(EMPTY_PAIR, COLOR_BLUE, COLOR_BLUE);
  clear();

  draw_board(); /* inicializa tabuleiro */

  for (size_t i = 0; i < TOKENS; i++)
  {
    tokenlife[i] = TRUE;
    tokenids[i] = i;
  }

  pthread_mutex_init(&mutex_token_reloc, NULL);
  pthread_mutex_init(&mutex_token_redraw, NULL);

  do
  {
    move_tokens();   /* move os tokens aleatoriamente */
    board_refresh(); /* redesenha tabuleiro */

    ch = getch();
    switch (ch)
    {
    case KEY_UP:
    case 'w':
    case 'W':
      if ((cursor.y > 0))
      {
        cursor.y = cursor.y - 1;
      }
      break;
    case KEY_DOWN:
    case 's':
    case 'S':
      if ((cursor.y < LINES - 1))
      {
        cursor.y = cursor.y + 1;
      }
      break;
    case KEY_LEFT:
    case 'a':
    case 'A':
      if ((cursor.x > 0))
      {
        cursor.x = cursor.x - 1;
      }
      break;
    case KEY_RIGHT:
    case 'd':
    case 'D':
      if ((cursor.x < COLS - 1))
      {
        cursor.x = cursor.x + 1;
      }
      break;
    }
  } while ((ch != 'q') && (ch != 'Q'));

  pthread_mutex_destroy(&mutex_token_reloc);
  pthread_mutex_destroy(&mutex_token_redraw);

  endwin();
  exit(0);
}

void move_tokens()
{
  // for (int i = 0; i < TOKENS; i++)
  //   move_token(i);

  for (int t = 0; t < TOKENS; t++)
    if (tokenlife[t] == TRUE)
      pthread_create(&threads[t], NULL, th_move_token, (void *)&tokenids[t]);

  for (int t = 0; t < TOKENS; t++)
    pthread_join(threads[t], NULL);
}

void board_refresh(void)
{
  pthread_mutex_lock(&mutex_token_redraw);
  int x, y, i;

  /* redesenha tabuleiro "limpo" */

  for (x = 0; x < COLS; x++)
  {
    for (y = 0; y < LINES; y++)
    {
      attron(COLOR_PAIR(EMPTY_PAIR));
      mvaddch(y, x, EMPTY);
      attroff(COLOR_PAIR(EMPTY_PAIR));
    }
  }

  /* poe os tokens no tabuleiro */

  for (i = 0; i < TOKENS; i++)
  {
    if (tokenlife[i])
    {
      attron(COLOR_PAIR(TOKEN_PAIR));
      mvaddch(coord_tokens[i].y, coord_tokens[i].x, EMPTY);
      attroff(COLOR_PAIR(TOKEN_PAIR));
    }
  }
  /* poe o cursor no tabuleiro */

  move(y, x);
  refresh();
  attron(COLOR_PAIR(CURSOR_PAIR));
  mvaddch(cursor.y, cursor.x, EMPTY);
  attroff(COLOR_PAIR(CURSOR_PAIR));
  pthread_mutex_unlock(&mutex_token_redraw);
}

void *th_move_token(void *args)
{
  pthread_mutex_lock(&mutex_token_reloc);
  int token = *((int *)args);
  move_token(token);
  pthread_mutex_unlock(&mutex_token_reloc);
  pthread_exit(EXIT_SUCCESS);
}

float inline dist(int i)
{
  int aux0 = coord_tokens[i].x - cursor.x;
  int aux1 = coord_tokens[i].y - cursor.y;
  int aux2 = aux0 * aux0;
  int aux3 = aux1 * aux1;
  return sqrt(aux2 + aux3);
}

void move_token(int token)
{
  int i = token, new_x, new_y;
  float distancia = dist(i);

  if (distancia > 0 && distancia <= 1)
  {
    tokenlife[i] = FALSE;
    distances[i] = distancia;
  }

  /* determina novas posicoes (coordenadas) do token no tabuleiro (matriz) */

  do
  {
    new_x = rand() % COLS;
    new_y = rand() % LINES;
  } while ((board[new_x][new_y] != -1) || ((new_x == cursor.x) && (new_y == cursor.y)));

  /* retira token da posicao antiga  */
  board[coord_tokens[i].x][coord_tokens[i].y] = 0;
  board[new_x][new_y] = token;

  /* coloca token na nova posicao */
  coord_tokens[i].x = new_x;
  coord_tokens[i].y = new_y;
}

void draw_board(void)
{
  int x, y;

  /* limpa matriz que representa o tabuleiro */
  for (x = 0; x < COLS; x++)
    for (y = 0; y < LINES; y++)
      board[x][y] = -1;
}
