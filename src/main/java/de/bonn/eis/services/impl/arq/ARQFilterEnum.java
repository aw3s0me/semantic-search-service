package de.bonn.eis.services.impl.arq;

import com.hp.hpl.jena.sparql.expr.*;
import com.hp.hpl.jena.sparql.expr.nodevalue.NodeValueInteger;
import com.hp.hpl.jena.sparql.syntax.ElementFilter;

/**
 * Created by korovin on 3/20/2017.
 */
public enum ARQFilterEnum {
    LESS("less"),
    GREATER("greater"),
    LESS_OR_EQUAL("lesseq"),
    GREATER_OR_EQUAL("greatereq"),
    EQUALS("greatereq");

    private final String value;

    private ARQFilterEnum(String text) {
        this.value = text;
    }

    public Expr getExpression(String variable, NodeValue value) {
        switch (this) {
            case LESS:
                return new E_LessThan(new ExprVar(variable), value);
            case GREATER:
                return new E_GreaterThan(new ExprVar(variable), value);
            case LESS_OR_EQUAL:
                return new E_LessThanOrEqual(new ExprVar(variable), value);
            case GREATER_OR_EQUAL:
                return new E_GreaterThanOrEqual(new ExprVar(variable), value);
            case EQUALS:
                return new E_Equals(new ExprVar(variable), value);
            default:
                return null;
        }
    }

    public static void main(String[] args) {
        Expr expr = ARQFilterEnum.LESS.getExpression("s", new NodeValueInteger(20));
        System.out.println(expr.toString());
        System.out.println((new ElementFilter(expr)).toString());
    }
}
