/**
 * JLibs: Common Utilities for Java
 * Copyright (C) 2009  Santhosh Kumar T
 * <p/>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 */

package jlibs.xml.sax.sniff.model.expr.bool;

import jlibs.xml.sax.sniff.model.Node;
import jlibs.xml.sax.sniff.model.ResultType;
import jlibs.xml.sax.sniff.model.expr.Expression;

import java.util.Collections;
import java.util.List;

/**
 * @author Santhosh Kumar T
 */
public class Relational extends Expression{
    public Relational(Node contextNode){
        super(contextNode, ResultType.BOOLEAN, ResultType.STRINGS, ResultType.STRINGS);
    }

    class MyEvaluation extends Evaluation{
        public List lhsResults;
        public List rhsResults;

        @Override
        public void finish(){
            if(lhsResults!=null && rhsResults!=null){
                ResultType lhsType = members.get(0).resultType();
                if(lhsType==ResultType.STRINGS)
                    lhsType = ResultType.STRING;

                ResultType rhsType = members.get(1).resultType();
                if(rhsType==ResultType.STRINGS)
                    rhsType = ResultType.STRING;

                for(Object lhs: lhsResults){
                    for(Object rhs: rhsResults){
                        if(evaluateObjectObject(lhs, rhs)){
                            setResult(true);
                            return;
                        }
                    }
                }
            }
            setResult(false);
        }

        private boolean evaluateObjectObject( Object lhs, Object rhs){
          if(lhs instanceof Boolean || rhs instanceof Boolean){
              boolean b1 = ResultType.asBoolean(lhs);
              boolean b2 = ResultType.asBoolean(rhs);
              return evaluateObjects(b1, b2);
          }else if(lhs instanceof Double || rhs instanceof Double){
              double d1 = ResultType.asNumber(lhs);
              double d2 = ResultType.asNumber(rhs);
              return evaluateObjects(d1, d2);
          }else{
              String s1 = ResultType.asString(lhs);
              String s2 = ResultType.asString(rhs);
              return evaluateObjects(s1, s2);
          }
        }

        private boolean evaluateObjects(Object lhs, Object rhs){
            if(lhs instanceof Double){
                if(Double.isNaN((Double)lhs) || Double.isNaN((Double)rhs))
                    return false;
            }
            return lhs.equals( rhs );
        }

        @Override
        protected void consume(Object member, Object result){
            if(member==members.get(0))
                lhsResults = toList(result);
            if(member==members.get(1))
                rhsResults = toList(result);
        }

        private List toList(Object result){
            if(result instanceof List)
                return (List)result;
            else
                return Collections.singletonList(result);
        }

        @Override
        protected void print(){}
    }

    @Override
    protected Evaluation createEvaluation(){
        return new MyEvaluation();
    }
}