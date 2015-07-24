/**
 * Copyright 2015 The JLibs Project
 *
 * The JLibs Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package jlibs.core.graph.visitors;

import jlibs.core.graph.Path;

/**
 * @author Santhosh Kumar T
 */
public abstract class PathReflectionVisitor<E, R> extends ReflectionVisitor<E, R>{
    protected Path path;

    @Override
    @SuppressWarnings({"unchecked"})
    public final R visit(E elem){
        if(elem instanceof Path){
            path = (Path)elem;
            elem = (E)path.getElement();
        }
        try{
            return _visit(elem);
        }finally{
            path = null;
        }
    }

    protected R _visit(E elem){
        return super.visit(elem);
    }
}