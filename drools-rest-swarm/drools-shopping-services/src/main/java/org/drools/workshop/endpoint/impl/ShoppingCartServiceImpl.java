package org.drools.workshop.endpoint.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.drools.workshop.endpoint.api.ShoppingCartService;
import org.drools.workshop.endpoint.exception.BusinessException;
import org.drools.workshop.model.Item;
import org.kie.api.cdi.KReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Inject
    @KReleaseId(groupId = "org.drools.workshop", artifactId = "drools-shopping-kjar", version = "1.0-SNAPSHOT")
    private KieContainer kContainer;

    private Map<String, KieSession> shoppingCarts = new HashMap<String, KieSession>();

    public ShoppingCartServiceImpl() {
    }

    @Override
    public String newShoppingCart() throws BusinessException{
        String cartId = UUID.randomUUID().toString();
        KieSession kSession = kContainer.newKieSession();
        shoppingCarts.put(cartId, kSession);
        return cartId;
    }

    @Override
    public void addItem(String id, Item item) throws BusinessException {
        if(shoppingCarts.get(id) == null){
            throw new BusinessException("The cart Id is not valid!");
        }
        shoppingCarts.get(id).insert(item);
        shoppingCarts.get(id).fireAllRules();
    }

    @Override
    public void removeItem(String id, Item item) throws BusinessException {
        if(shoppingCarts.get(id) == null){
            throw new BusinessException("The cart Id is not valid!");
        }
        FactHandle factHandle = shoppingCarts.get(id).getFactHandle(item);
        shoppingCarts.get(id).delete(factHandle);
    }

    @Override
    public void checkout(String id) throws BusinessException {
        if(shoppingCarts.get(id) == null){
            throw new BusinessException("The cart Id is not valid!");
        }
        System.out.println(">> Thanks for buying with us.");
        for (Object o : shoppingCarts.get(id).getObjects()) {
            System.out.println("\t\t > " + o);
        }
        shoppingCarts.get(id).dispose();
        shoppingCarts.remove(id);

    }

    @Override
    public void empty(String id) throws BusinessException {
        if(shoppingCarts.get(id) == null){
            throw new BusinessException("The cart Id is not valid!");
        }
        KieSession kSession = kContainer.newKieSession();
        shoppingCarts.put(id, kSession);
    }

    @Override
    public List<Item> getItems(String id) throws BusinessException {
        if(shoppingCarts.get(id) == null){
            throw new BusinessException("The cart Id is not valid!");
        }
        List<Item> items = new ArrayList<Item>();
        for (Object o : shoppingCarts.get(id).getObjects()) {
            items.add((Item) o);
        }
        return items;
    }

    @Override
    public void removeShoppingCart(String id) throws BusinessException {
        if(shoppingCarts.get(id) == null){
            throw new BusinessException("The cart Id is not valid!");
        }
        shoppingCarts.get(id).dispose();
        shoppingCarts.remove(id);
    }

    @Override
    public Set<String> getShoppingCarts() throws BusinessException {
        return shoppingCarts.keySet();
    }
    
    
    

}
