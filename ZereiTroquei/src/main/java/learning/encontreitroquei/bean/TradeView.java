package learning.encontreitroquei.bean;

import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import learning.encontreitroquei.model.*;
import learning.encontreitroquei.service.ProductService;
import learning.encontreitroquei.service.TradeService;
import learning.encontreitroquei.service.UserService;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

@Named
@ViewScoped
public class TradeView implements Serializable {

    @Inject
    private ProductView productView;

    @Inject
    private ActiveTradeView activeTradeView;

    private transient ProductService productService;
    private transient TradeService tradeService;
    private transient UserService userService;

    private User source;
    private User target;
    private List<Product> offer = new ArrayList<>();
    private Product desiredProduct;
    private DualListModel<Product> trade;

    public TradeView() {
        productService = new ProductService();
        tradeService = new TradeService();
        userService = new UserService();
    }

    @PostConstruct
    public void init() {
        offer = new ArrayList<>();
        trade = new DualListModel<>(productView.getUserProducts(), offer);
    }

    public User getSource() {
        return source;
    }

    public void setSource(User source) {
        this.source = source;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public List<Product> getOffer() {
        return offer;
    }

    public void setOffer(List<Product> offer) {
        this.offer = offer;
    }

    public Product getDesiredProduct() {
        return desiredProduct;
    }

    public void setDesiredProduct(Product desiredProduct) {
        this.desiredProduct = desiredProduct;
    }

    public DualListModel<Product> getTrade() {
        return trade;
    }

    public void setTrade(DualListModel<Product> trade) {
        this.trade = trade;
    }

    public void submit() {
        FacesContext context = FacesContext.getCurrentInstance();
        List<Product> prods = source.getProducts();

        for (Object o : trade.getTarget()) {
            Product p = new Product();
            p.setId(Integer.valueOf(o.toString()));
            offer.add(prods.get(prods.indexOf(p)));
        }

//        if (source != null && target != null && offer != null && desiredProduct != null) {
//            if (trade.getTarget().size() <= 0) {
//                PrimeFaces.current().ajax().addCallbackParam("validationFailed", true);
//                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Oferta falhou", "Oferta precisa ter 1 ou + itens."));
//            } else {
//                if (source.equals(target)) {
//                    PrimeFaces.current().ajax().addCallbackParam("validationFailed", true);
//                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Oferta falhou", "Você não pode trocar consigo mesmo."));
//                } else {
        Trade auxTradeOffer = new Trade();
        auxTradeOffer.setSource(source);
        auxTradeOffer.setTarget(target);
        auxTradeOffer.setOffer(offer);
        auxTradeOffer.setDesiredProduct(desiredProduct);

        Set<Trade> auxSourceTrades = source.getTrades();
        auxSourceTrades.add(auxTradeOffer);
        source.setTrades(auxSourceTrades);

        Set<Trade> auxTargetTrades = target.getTrades();
        auxTargetTrades.add(auxTradeOffer);
        target.setTrades(auxTargetTrades);

        tradeService.saveOrUpdate(auxTradeOffer);
        userService.saveOrUpdate(source);
        userService.saveOrUpdate(target);

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Oferta bem sucedida", "Registrando-a. Aguarde confirmação."));

        activeTradeView.updateCurrentTrades();
        PrimeFaces.current().ajax().addCallbackParam("validationFailed", false);
        PrimeFaces.current().executeScript("PF(':offerDialog').hide();");
        PrimeFaces.current().ajax().update(":everybodyProductsForm:tradeFormSpec:listOfOffers");

//                }
//            }
//        } else {
//            PrimeFaces.current().ajax().addCallbackParam("validationFailed", true);
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Oferta falhou", "Sistema não conseguiu selecionar o dono do produto escolhido. Desculpe, tente novamente."));
//        }
    }

    public void onTransfer(TransferEvent event) {
        StringBuilder builder = new StringBuilder();

        for (Object item : event.getItems()) {
            builder.append(item.toString()).append("\n\n");
        }

        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Oferta Criada, Agora o outro usuário precisa aceitar.");
        msg.setDetail(builder.toString());

        FacesContext.getCurrentInstance().addMessage(null, msg);
        PrimeFaces.current().ajax().update(":everybodyProductsForm:tradeFormSpec:listOfOffers");
    }

    public void onSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Produto Selecionado", event.getObject().toString()));
    }

    public void onUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Produto De-selecionado", event.getObject().toString()));
    }

    public void onReorder() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Lista Ordenada", null));
    }
}
