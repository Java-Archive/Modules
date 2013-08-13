package org.rapidpm.demo.cdi.commons.messagebus;

import javax.enterprise.util.AnnotationLiteral;

/**
 * User: Sven Ruppert
 * Date: 31.07.13
 * Time: 10:19
 *
 * q : AnnotationLiteral from the modul, like a Scope f communication
 * t: type that is handled by the message
 *
 *
 */
public class Message<T> {

    private AnnotationLiteral annotationLiteral;

    private T value ;


    public AnnotationLiteral getAnnotationLiteral() {
        return annotationLiteral;
    }

    public void setAnnotationLiteral(AnnotationLiteral annotationLiteral) {
        this.annotationLiteral = annotationLiteral;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("annotationLiteral=").append(annotationLiteral);
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
