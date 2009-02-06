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

package jlibs.xml.sax.sniff.model.expr.string;

import jlibs.core.lang.ImpossibleException;
import jlibs.xml.sax.sniff.model.Node;
import jlibs.xml.sax.sniff.model.ResultType;
import jlibs.xml.sax.sniff.model.UserResults;
import jlibs.xml.sax.sniff.model.expr.Expression;

/**
 * @author Santhosh Kumar T
 */
public class Concat extends Expression{
    public Concat(Node contextNode){
        super(contextNode, ResultType.STRING, ResultType.STRING, ResultType.STRING);
    }

    public ResultType memberType(int index){
        return ResultType.STRING;
    }

    @Override
    public void addMember(UserResults member){
        addMember(member, ResultType.STRING);
    }

    class MyEvaluation extends Evaluation{
        private int pending = members.size();
        private String results[] = new String[pending];
        
        @Override
        public void finish(){
            throw new ImpossibleException();
        }

        @Override
        protected void consume(Object member, Object result){
            int i = 0;
            for(UserResults _member: members){
                if(_member==member){
                    results[i] = (String)result;
                    pending--;
                    if(pending==0){
                        StringBuilder buff = new StringBuilder();
                        for(String str: results)
                            buff.append(str);
                        setResult(buff.toString());
                        return;
                    }
                }
                i++;
            }
        }

        @Override
        protected void print(){}
    }

    @Override
    protected Evaluation createEvaluation(){
        return new MyEvaluation();
    }
}