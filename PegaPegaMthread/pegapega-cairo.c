// sudo apt install libgtk-3-dev
// gcc -o pegapega pegapega.c `pkg-config --cflags gtk+-3.0` `pkg-config --libs gtk+-3.0` -lpthread -lm
// ./pegapega

#include <stdlib.h>
#include <math.h>
#include <pthread.h>
#include <stdbool.h>

#include <cairo/cairo.h>
#include <gtk/gtk.h>
#include <gdk/gdk.h>
#include <gdk/gdkkeysyms.h>

#define TOKENS 5

#define WIN_WIDTH 1024
#define WIN_HEIGHT 768

struct tag_token_t
{
  double x, y;       /* x and y positions */
  int id;            /* token id */
  int width, height; /* width and height */
  bool life;         /* token life */
  bool action;       /* thread activation */
  double vx, vy;     /* x and y velocities */
  double r, g, b;    /* colour components */
};

typedef struct tag_token_t token_t;

token_t token_body[TOKENS];

token_t player;

GtkWidget *darea;

int key_press_active, button_press_active;

pthread_mutex_t mutex_token_reloc = PTHREAD_MUTEX_INITIALIZER;

pthread_mutex_t mutex_token_redraw = PTHREAD_MUTEX_INITIALIZER;

pthread_t th_token_animation_set[TOKENS];

pthread_t player_thread;

static gboolean key_press_event(GtkWidget *widget, GdkEventKey *event);
void init_tokens();
void animate();
void *th_animate(void *ptr);
void *th_animate_player(void *ptr);
gboolean token_animate(GtkWidget *window);
static gboolean on_draw_event(GtkWidget *widget, cairo_t *cr, gpointer user_data);
static gboolean test_within_bounds();
static gboolean test_distance(int i);

static gboolean test_within_bounds()
{
  if (player.x < player.width || player.x > (WIN_WIDTH - player.width) || player.y < player.height || player.y > (WIN_HEIGHT - player.height))
    return FALSE;
  return TRUE;
}

static gboolean key_press_event(GtkWidget *widget, GdkEventKey *event)
{
  // GtkWidget *window;
  // g_printerr("Key Press %s\n", gdk_keyval_name(event->keyval));
  switch (event->keyval)
  {
  case GDK_KEY_w:
  case GDK_KEY_Up:
    // g_printerr("Up!\n");
      player.y -= player.vy;
    break;
  case GDK_KEY_s:
  case GDK_KEY_Down:
    // g_printerr("Down!\n");
      player.y += player.vy;
    break;
  case GDK_KEY_a:
  case GDK_KEY_Left:
    // g_printerr("Left!\n");
      player.x -= player.vx;
    break;
  case GDK_KEY_d:
  case GDK_KEY_Right:
    // g_printerr("Right!\n");
      player.x += player.vx;
    break;
  case GDK_KEY_q:
  case GDK_KEY_Escape:
    g_printerr("Mutexes Destroyed!\n");
    g_printerr("Exit!\n");

    pthread_mutex_destroy(&mutex_token_reloc);
    pthread_mutex_destroy(&mutex_token_redraw);

    exit(EXIT_SUCCESS);
    // break;
  default:
    break;
  }

  key_press_active = 1;
  return FALSE;
}

void init_tokens()
{
  player.id = TOKENS; /* number of tokens is the player id */
  player.x = (int)WIN_WIDTH / 2;
  player.y = (int)WIN_WIDTH / 2;
  player.width = 30;
  player.height = 30;
  player.vx = 15;
  player.vy = 15;
  player.life = TRUE;
  player.action = TRUE;
  player.r = 255.0;
  player.g = 255.0;
  player.b = 255.0;

  for (size_t n = 0; n < TOKENS; n++)
  {
    token_body[n].id = n;
    token_body[n].x = rand() % WIN_WIDTH;
    token_body[n].y = rand() % WIN_HEIGHT;
    token_body[n].width = 30;
    token_body[n].height = 30;
    token_body[n].life = TRUE;
    token_body[n].action = FALSE;
    token_body[n].vx = 0.00005;
    token_body[n].vy = 0.00005;
    token_body[n].r = (rand() % 255) / 255.0;
    token_body[n].g = (rand() % 255) / 255.0;
    token_body[n].b = (rand() % 255) / 255.0;
  }
}

static gboolean test_distance(int id)
{
  double aux0 = token_body[id].x - player.x;
  double aux1 = token_body[id].y - player.y;
  double aux2 = aux0 * aux0;
  double aux3 = aux1 * aux1;
  double distancia = sqrt(aux2 + aux3);
  return distancia > 0.0 && distancia <= (player.width >> 1);
}

void *th_animate(void *ptr)
{
  int n = *((int *)ptr);

  token_body[n].action = TRUE;

  while (1)
  {
    pthread_mutex_lock(&mutex_token_reloc);
    pthread_mutex_lock(&mutex_token_redraw);

    token_body[n].x += token_body[n].vx;

    if (token_body[n].x < token_body[n].width || token_body[n].x > (WIN_WIDTH - token_body[n].width))
    {
      token_body[n].vx *= -1;
      // token_body[n].x += (token_body[n].vx * 2);
    }

    token_body[n].y += token_body[n].vy;

    if (token_body[n].y < token_body[n].height || token_body[n].y > (WIN_HEIGHT - token_body[n].height))
    {
      token_body[n].vy *= -1;
      // token_body[n].y += (token_body[n].vy * 2);
    }

    pthread_mutex_unlock(&mutex_token_reloc);
    pthread_mutex_unlock(&mutex_token_redraw);

    if (test_distance(token_body[n].id)) {
      token_body[n].r = 90;
      token_body[n].g = 20;
      token_body[n].b = 40;
      pthread_exit(EXIT_SUCCESS);
    }
  }

}

void animate()
{
  for (size_t n = 0; n < TOKENS; n++)
  {
    token_body[n].x += token_body[n].vx;

    if (token_body[n].x < token_body[n].width || token_body[n].x > (WIN_WIDTH - token_body[n].width))
    {
      token_body[n].vx *= -1;
      token_body[n].x += (token_body[n].vx * 2);
    }

    token_body[n].y += token_body[n].vy;

    if (token_body[n].y < token_body[n].height || token_body[n].y > (WIN_HEIGHT - token_body[n].height))
    {
      token_body[n].vy *= -1;
      token_body[n].y += (token_body[n].vy * 2);
    }
  }
}

gboolean game(GtkWidget *window)
{
  // animate();
  // g_printerr("Animate!\n");

  // pthread_create(&player_thread, NULL, th_animate_player, (void*)&player.id);

  for (size_t n = 0; n < TOKENS; n++)
  {
    if (!token_body[n].action)
    {
      pthread_create(&th_token_animation_set[n], NULL, th_animate, (void *)&token_body[n].id);
      // g_printerr("Thread [%d] Created!\n", token_body[n].id);
    }
  }

  // for (size_t n = 0; n < TOKENS; n++)
  //   if (token_body[n].action)
  //     pthread_join(th_token_animation_set[n], NULL);

  gtk_widget_queue_draw_area(window, 0, 0, WIN_WIDTH, WIN_HEIGHT);
  return TRUE;
}

static gboolean on_draw_event(GtkWidget *widget, cairo_t *cr, gpointer user_data)
{
  int n;

  /* clear the background to black */
  cairo_set_source_rgb(cr, 0, 0, 0);
  cairo_paint(cr);

  cairo_set_source_rgb(cr, player.r, player.g, player.b);
  cairo_rectangle(cr, player.x, player.y, player.width, player.height);
  cairo_set_line_width(cr, 14);
  cairo_set_line_join(cr, CAIRO_LINE_JOIN_MITER);
  cairo_move_to(cr, player.x, player.y);
  cairo_set_line_width(cr, 1);
  cairo_fill(cr);

  for (n = 0; n < TOKENS; n++)
  {
    // pthread_join(th_token_animation_set[n], NULL);
    if (token_body[n].life) {
      pthread_mutex_lock(&mutex_token_redraw);
      cairo_set_source_rgb(cr, token_body[n].r, token_body[n].g, token_body[n].b);
      cairo_rectangle(cr, token_body[n].x, token_body[n].y, token_body[n].width, token_body[n].height);
      cairo_set_line_width(cr, 14);
      cairo_set_line_join(cr, CAIRO_LINE_JOIN_MITER);

      cairo_move_to(cr, token_body[n].x, token_body[n].y);

      cairo_set_line_width(cr, 1);
      cairo_fill(cr);
      pthread_mutex_unlock(&mutex_token_redraw);
    }
  }

  return FALSE;
}

int main(int argc, char *argv[])
{
  srand(time(NULL));

  GtkWidget *window;

  gtk_init(&argc, &argv);

  init_tokens();

  // for (size_t n = 0; n < TOKENS; n++)
  //   pthread_create(&th_token_animation_set[n], NULL, th_animate, (void *)&token_body[n].id);

  window = gtk_window_new(GTK_WINDOW_TOPLEVEL);

  darea = gtk_drawing_area_new();
  gtk_container_add(GTK_CONTAINER(window), darea);

  g_signal_connect(G_OBJECT(darea), "draw", G_CALLBACK(on_draw_event), NULL);
  g_signal_connect(window, "destroy", G_CALLBACK(gtk_main_quit), NULL);
  g_signal_connect(window, "key-press-event", G_CALLBACK(key_press_event), NULL);

  gtk_window_set_position(GTK_WINDOW(window), GTK_WIN_POS_CENTER);
  gtk_window_set_default_size(GTK_WINDOW(window), WIN_WIDTH, WIN_HEIGHT);
  gtk_window_set_title(GTK_WINDOW(window), "Pega pega de tokens");

  gtk_widget_show_all(window);

  (void)g_timeout_add(33, (GSourceFunc)game, window);

  gtk_main();

  return 0;
}
