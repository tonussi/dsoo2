package learning.encontreitroquei.bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import learning.encontreitroquei.model.Product;
import learning.encontreitroquei.model.User;
import learning.encontreitroquei.service.ProductService;
import learning.encontreitroquei.service.UserService;
import learning.encontreitroquei.util.Util;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.shaded.commons.io.IOUtils;

@Named
@ViewScoped
public class ProductRegisterView implements Serializable {

    @Inject
    private UserView userView;

    @Inject
    private ProductView productView;

    private transient ProductService productService;

    private Float preco = (float) Math.random() * 100;
    private String nome = "TÍTULO QUALQUER " + (int) Math.abs(Math.random() * 1000);
    private String descricao = "DESCRIÇÃO QUALQUER " + (int) Math.abs(Math.random() * 1000);
    private String dataCompra = Util.formatDate(new Date());
    private String graphic;
    private List<String> graphics = new ArrayList<>();
    private final String destination = "images/games/";
    private String console;
    private String ownerEmail;
    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void handleUpload(FileUploadEvent event) {
        if (event.getFile() != null) {
            FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);

            graphic = event.getFile().getFileName();

            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            Set<String> resources = ctx.getResourcePaths("images/games");
            System.out.println(resources);

            try {
                InputStream in = file.getInputstream();
                File targetFile = new File(destination + event.getFile().getFileName());
                java.nio.file.Files.copy(in, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                IOUtils.closeQuietly(in);
            } catch (IOException ex) {
                Logger.getLogger(ProductRegisterView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void upload() {
        if (file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);

            graphic = file.getFileName();

            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            Set<String> resources = ctx.getResourcePaths("/resources/images/games/");
            System.out.println(resources);

            try {
                InputStream in = file.getInputstream();
                File targetFile = new File(destination + file.getFileName());
                java.nio.file.Files.copy(in, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                IOUtils.closeQuietly(in);
            } catch (IOException ex) {
                Logger.getLogger(ProductRegisterView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getGraphic() {
        return graphic;
    }

    public void setGraphic(String graphic) {
        this.graphic = graphic;
    }

    private static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public ProductRegisterView() {
        productService = new ProductService();
        graphics.add("cod.png");
        graphics.add("fifa19.png");
        graphics.add("metalgear.png");
        graphics.add("sekiro.jpg");
        graphic = graphics.get(getRandomNumberInRange(0, graphics.size() - 1));
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public void submit() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        UserService userService = new UserService();
        User user = userService.findByEmail(userView.getEmail());

        if (user == null) {

            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Produto não inserido (precisa logar no sistema)", "");

        } else {

            Product product = new Product();
            product.setConsole(console);
            product.setDataCompra(Util.parseDate(dataCompra));
            product.setDescricao(descricao);
            product.setOwner(user);
            product.setPreco(preco);
            product.setNome(nome);
            product.setGraphic(graphic);

            List<Product> products = user.getProducts();
            products.add(product);
            user.setProducts(products);

            product.setOwner(user);

            productService.saveOrUpdate(product);

            userService.saveOrUpdate(user);

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Produto Inserido com Sucesso", product.toString());

            productView.updateAllProductsList();
            PrimeFaces.current().ajax().update(":everybodyProductsForm:everybodyProductsDataGrid");
            PrimeFaces.current().ajax().update(":everybodyProductsForm:tradeDialog");
        }

        facesContext.addMessage(null, message);
    }
}
