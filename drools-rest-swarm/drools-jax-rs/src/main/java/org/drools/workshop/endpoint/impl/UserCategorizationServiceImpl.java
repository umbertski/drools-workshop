package org.drools.workshop.endpoint.impl;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.drools.workshop.model.User;

import org.drools.workshop.endpoint.api.UserCategorizationService;
//import org.kie.api.cdi.KReleaseId;
// import org.kie.api.cdi.KSession;

import org.kie.api.KieBase;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.KieServices;


/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class UserCategorizationServiceImpl implements UserCategorizationService {

//    @Inject
//   @KSession
//    @KReleaseId(groupId = "org.drools.workshop", artifactId = "drools-user-kjar", version = "1.0-SNAPSHOT")
    private KieSession kSession;
    private KieContainer kieContainer;
    private KieBase kieBase = null;

    public UserCategorizationServiceImpl() {
    }

    @Override
    public User categorizeUser(User user) {
        if (kieBase == null) {
            createKieBase();
          }
        kSession = kieBase.newKieSession();
        System.out.println(">> kSession: " + kSession);
        printKieSessionAllFacts(kSession);
        System.out.println(">> User: " + user);
        kSession.insert(user);
        int fired = kSession.fireAllRules();
        System.out.println(">> Fired: " + fired);
        return user;

    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        for (Object o : kSession.getObjects()) {
            if (o instanceof User) {
                users.add((User) o);
            }
        }
        return users;
    }

    private void printKieSessionAllFacts(KieSession kSession) {
        System.out.println(" >> Start - Printing All Facts in the Kie Session");
        for (Object o : kSession.getObjects()) {
            System.out.println(">> Fact: " + o);
        }
        System.out.println(" >> End - Printing All Facts in the Kie Session");
    }

    private void createKieBase() {
        //Helper.setDebug(true);
        System.out.println(">> Loading rules");
        KieServices kieServices = KieServices.Factory.get();
        //kieContainer = kieServices.getKieClasspathContainer();
        ReleaseId releaseId = 
            kieServices.newReleaseId( "org.drools.workshop", "drools-user-kjar", "1.0-SNAPSHOT" );
        
        kieContainer = kieServices.newKieContainer( releaseId );    
        kieBase = kieContainer.getKieBase();
      }
}
