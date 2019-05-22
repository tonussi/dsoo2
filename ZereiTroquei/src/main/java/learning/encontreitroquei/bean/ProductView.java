package learning.encontreitroquei.bean;

import java.io.Serializable;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import learning.encontreitroquei.model.*;
import learning.encontreitroquei.service.*;
import org.primefaces.PrimeFaces;

@Named
@ViewScoped
public class ProductView implements Serializable {

    @Inject
    private UserView userView;

    @Inject
    private TradeView tradeView;

    private transient ProductService productService;

    private transient UserService userService;

    public ProductView() {
        productService = new ProductService();
        userService = new UserService();

        allProducts = new ArrayList<>(productService.findAll(Product.class));

        consoles.add("Xbox One X");
        consoles.add("PlayStation 4 Pro");
        consoles.add("Nintendo Switch");
    }

    private List<Product> userProducts = new ArrayList<>();

    private List<Product> allProducts = new ArrayList<>();

    private Set<String> consoles = new HashSet<>();

    private Product selectedProduct;

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public void updateAllProductsList() {
        allProducts = new ArrayList<>(productService.findAll(Product.class));
    }

    public List<Product> getUserProducts() {
        setupUserProductsByEmail();
        return userProducts;
    }

    public void setUserProducts(List<Product> userProducts) {
        this.userProducts = userProducts;
    }

    public Set<String> getConsoles() {
        return consoles;
    }

    public void setConsoles(Set<String> consoles) {
        this.consoles = consoles;
    }

    public List<Product> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(List<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public void setupUserProductsByEmail() {
        User user = userService.findByEmail(userView.getEmail());

        if (user != null) {
            userProducts = user.getProducts();
        } else {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Problemas", "É preciso estár logado no sistema");
            facesContext.addMessage(null, message);
        }

    }

    public void deleteProduct(Product p) {
        if (p != null) {
            User user = userService.findByEmail(userView.getEmail());
            List<Product> products = user.getProducts();
            products.remove(p);
            user.setProducts(products);
            userService.saveOrUpdate(user);
            productService.delete(p);
            userProducts.remove(p);

            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", p.getId() + " foi deletado.");
            facesContext.addMessage(null, message);

            updateAllProductsList();
            PrimeFaces.current().ajax().update(":everybodyProductsForm:everybodyProductsDataGrid");
        } else {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Nada foi removido.");
            facesContext.addMessage(null, message);
        }
    }

    public void setupTrade(Product product) {
        if (product.getOwner().getEmail().equals(userView.getEmail())) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Aviso", "Esse produto já é seu. Produto: "
                    + product.getNome() + " de "
                    + product.getOwner().getNome() + ".");
            facesContext.addMessage(null, message);
        } else {
            User source = userService.findByEmail(userView.getEmail());
            tradeView.setDesiredProduct(product);
            tradeView.setTarget(product.getOwner());
            tradeView.setSource(source);

            PrimeFaces.current().ajax().update(":everybodyProductsForm:tradeDialog");
            PrimeFaces.current().executeScript("PF('offerDialog').show()");

            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Aviso", "Troca Iniciada, Ofereça produtos por "
                    + product.getNome() + " de "
                    + product.getOwner().getNome() + ".");
            facesContext.addMessage(null, message);
        }
    }

//    public void exchangeProduct() {
//        if (selectedProduct != null && selectedProductForExchange != null) {
//            User user = userService.findByEmail(userView.getEmail());
//            User targetUser = userService.findByEmail(targetUserForExchangeProduct.getEmail());
//
//            List<Product> products = user.getProducts();
//            products.remove(selectedProduct);
//            user.setProducts(products);
//
//            List<Product> targetUserProducts = targetUser.getProducts();
//            targetUserProducts.add(selectedProductForExchange);
//            targetUser.setProducts(targetUserProducts);
//
//            userService.saveOrUpdate(user);
//            userService.saveOrUpdate(targetUserProducts);
//
//            updateAllProductsList();
//            PrimeFaces.current().ajax().update(":everybodyProductsForm:everybodyProductsDataGrid");
//        }
//    }
}
