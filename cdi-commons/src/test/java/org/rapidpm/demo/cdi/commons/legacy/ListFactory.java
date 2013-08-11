package org.rapidpm.demo.cdi.commons.legacy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.rapidpm.demo.cdi.commons.registry.ContextResolver;

/**
 * Created by Sven Ruppert on 02.08.13.
 */
public class ListFactory {


    public List createArrayList(){
        return new ArrayList();
    }

    public List createLinkedList(){
        return new LinkedList();
    }

    public List createList(){
        return new ArrayList();
    }
    public List createList(final ContextResolver contextResolver){
        //trivial Implementierung
        if(contextResolver == null){
            return createArrayList();
        } else{
            //triviale Fallunterscheidung
            if(contextResolver.resolveContext().equals(null)){
                return createArrayList();
            } else{
                return createLinkedList();
            }
        }
    }
}
