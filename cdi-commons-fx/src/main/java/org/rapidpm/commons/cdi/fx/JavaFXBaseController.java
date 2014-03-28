package org.rapidpm.commons.cdi.fx;

import javafx.application.Platform;
import org.rapidpm.commons.cdi.CDINotMapped;
import org.rapidpm.commons.cdi.logger.CDILogger;
import org.rapidpm.lang.CachedThreadPoolSingleton;
import org.rapidpm.module.se.commons.logger.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Created by Sven Ruppert
 */
@CDINotMapped
public abstract class JavaFXBaseController implements CDIJavaFxBaseController {

    public static final String DONE = "done";

    private boolean mockModusActive = false;
    public boolean isMockModusActive() {
        return mockModusActive;
    }
    public void setMockModusActive(boolean mockModusActive) {
        this.mockModusActive = mockModusActive;
    }

    public abstract void cleanUp();

    public abstract void setI18n();

    private @Inject @CDILogger Logger logger;

    private Boolean initCompleteCDI = false;
    private Boolean initCompleteFX = false;

    public CompletableFuture<String> supplyAsync;

    @Override
    public void initInstance(){
        supplyAsync = CompletableFuture.supplyAsync(task, CachedThreadPoolSingleton.getInstance().cachedThreadPool);
        if (logger.isDebugEnabled()) supplyAsync.thenAccept(logger::debug);  //logger
    }

    public final Supplier<String> task = ()-> {
//        Warten bis alle true
        while(! (initCompleteCDI && initCompleteFX) ){
            try {
                //evtl loggen
                System.out.println("initCompleteCDI = " + initCompleteCDI);
                System.out.println("initCompleteFX = " + initCompleteFX);
                System.out.println("getClass().getName() = " + getClass().getName());
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return e.toString();
            }
        }
        System.out.println("initBusinessLogic() => called now");
        final boolean fxApplicationThread = Platform.isFxApplicationThread();
        if ( ! fxApplicationThread){
            Platform.runLater(this::initBusinessLogic);
        } else {
            initBusinessLogic();
        }


        System.out.println("initBusinessLogic() => done now");
        return DONE;
    };

    @PostConstruct
    public final void postconstruct(){
        if (logger.isDebugEnabled()) {
            logger.debug("PostConstruct mockModusActive == " + mockModusActive);
        }
        cdiPostConstruct();
        initCompleteCDI = true;
        if (logger.isDebugEnabled()) {
            logger.debug("postconstruct ready " + getClass().getName());
        }
    }

    public abstract void cdiPostConstruct();

    @Override
    public final void initialize(URL url, ResourceBundle resourceBundle) {
        if (logger.isDebugEnabled()) {
            logger.debug("initialize mockModusActive== " + mockModusActive);
        }
        initializeFX(url, resourceBundle);
        initCompleteFX = true;
        if (logger.isDebugEnabled()) {
            logger.debug("initialize ready " + getClass().getName());
        }
    }


    protected abstract void initializeFX(URL url, ResourceBundle resourceBundle);
    /**
     * wird nach der init von CDI und JavaFX aufgerufen,
     * egal in welcher Reihenfolge die init durchlaufen wird.
     *
     * ein blockierender method call
     *
     */
    public abstract void initBusinessLogic();
}