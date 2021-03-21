package fr.carbonit.treasurehunt.businessservice.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

public abstract class BaseBS {
    private static final Log trace = LogFactory.getLog(BaseBS.class);

    protected static ApplicationContext cont = null;

    public BaseBS() {
    }

    public static <T> T getBS(Class classe) {
        BaseBS base = null;
        try {
            base =  (BaseBS) cont.getBean(classe);
        } catch (BeansException e){
            trace.error("Erreur de récupération d'un BS ", e);
        }

        return (T) base;
    }

    public static void setContexte(ApplicationContext contexte) {
        cont = contexte;
    }
}
