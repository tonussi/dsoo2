package learning.encontreitroquei.bean;

import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import learning.encontreitroquei.model.Product;
import learning.encontreitroquei.model.Trade;
import learning.encontreitroquei.model.User;
import learning.encontreitroquei.service.ProductService;
import learning.encontreitroquei.service.TradeService;
import learning.encontreitroquei.service.UserService;
import org.primefaces.PrimeFaces;

@Named
@SessionScoped
public class ActiveTradeView implements Serializable {

    @Inject
    private UserView userView;

    private transient UserService userService;
    private transient TradeService tradeService;
    private transient ProductService productService;

    private Set<Trade> userTrades;

    private User user;

    private Trade selectedTrade;

    @PostConstruct
    public void init() {
        userService = new UserService();
        tradeService = new TradeService();
        productService = new ProductService();
        updateCurrentTrades();
    }

    public ActiveTradeView() {
    }

    public void updateCurrentTrades() {
        user = userService.findByEmail(userView.getEmail());
        userTrades = user.getTrades();
    }

    public Set<Trade> getUserTrades() {
        return userTrades;
    }

    public void setUserTrades(Set<Trade> userTrades) {
        this.userTrades = userTrades;
    }

    public Trade getSelectedTrade() {
        return selectedTrade;
    }

    public void setSelectedTrade(Trade selectedTrade) {
        this.selectedTrade = selectedTrade;
    }

    public boolean canAccept(Trade trade) {
        if (trade == null) {
            return false;
        }
        return userView.getId().equals(trade.getSource().getId());
    }

    public void closeTrade(Trade trade) {
        if (trade != null) {
            User source = trade.getSource();
            User target = trade.getTarget();

            Set<Trade> sourceTradeSide = source.getTrades();
            sourceTradeSide.remove(trade);
            target.setTrades(sourceTradeSide);

            Set<Trade> targetTradeSide = target.getTrades();
            targetTradeSide.remove(trade);
            target.setTrades(targetTradeSide);

            userService.saveOrUpdate(source);
            userService.saveOrUpdate(target);

            List<Product> sourceOffer = trade.getOffer();
            sourceOffer.clear();
            trade.setOffer(sourceOffer);

            tradeService.saveOrUpdate(trade);
            tradeService.delete(trade);

            updateCurrentTrades();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Deletado");
            facesContext.addMessage(null, message);
            PrimeFaces.current().ajax().update(":userActiveTradesForm:userActiveTradesDisplay");
        }
    }

    public void acceptTrade(Trade trade) {
        if (trade != null) {
            if (trade.getSource().getEmail().equalsIgnoreCase(userView.getEmail())) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Você não pode ACEITAR a sua oferta.");
                facesContext.addMessage(null, message);
                PrimeFaces.current().ajax().update(":userActiveTradesForm:userActiveTradesDisplay");
            } else {
                List<Product> productsBeingOffered = trade.getOffer();
                User source = trade.getSource();
                User target = trade.getTarget();

                List<Product> sourceProducts = source.getProducts();
                sourceProducts.removeAll(productsBeingOffered);
                source.setProducts(sourceProducts);

                userService.saveOrUpdate(source);

                List<Product> targetProducts = target.getProducts();
                targetProducts.remove(trade.getDesiredProduct());
                target.setProducts(targetProducts);

                userService.saveOrUpdate(target);

                targetProducts.addAll(productsBeingOffered);
                target.setProducts(targetProducts);

                for (Product product : productsBeingOffered) {
                    product.setOwner(target);
                    productService.saveOrUpdate(productsBeingOffered);
                }

                userService.saveOrUpdate(target);

                Product desiredProduct = trade.getDesiredProduct();
                sourceProducts.add(desiredProduct);

                desiredProduct.setOwner(source);

                productService.saveOrUpdate(desiredProduct);

                userService.saveOrUpdate(source);

                closeTrade(trade);

                FacesContext facesContext = FacesContext.getCurrentInstance();
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Transação aceita.");
                facesContext.addMessage(null, message);
                PrimeFaces.current().ajax().update(":userActiveTradesForm:userActiveTradesDisplay");
                PrimeFaces.current().executeScript("PF(':userActiveTradesForm:media_exchange_completed').Play();");
            }
        }
    }
}
