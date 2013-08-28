package org.rapidpm.demo.cdi.commons.locale;

import java.util.Locale;

import javax.enterprise.inject.Produces;

/**
 * User: Sven Ruppert
 * Date: 20.06.13
 * Time: 09:02
 */
public class LocaleProducer {


    @Produces
    @CDILocale
    public Locale createDefaultLocale(){
        return new Locale("de", "DE"); //JIRA MOD-46 Definition der default Locale
    }

}
