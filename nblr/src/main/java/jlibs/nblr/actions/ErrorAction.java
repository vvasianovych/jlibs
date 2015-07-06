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

package jlibs.nblr.actions;

import jlibs.core.lang.StringUtil;

/**
 * @author Santhosh Kumar T
 */
public class ErrorAction implements Action{
    public final String errorMessage;

    public ErrorAction(String errorMessage){
        this.errorMessage = errorMessage;
    }

    @Override
    public String javaCode(){
        return "throw handler.fatalError(\""+ StringUtil.toLiteral(errorMessage, false)+"\")";
    }

    @Override
    public String toString(){
        return "error(\""+StringUtil.toLiteral(errorMessage, false)+"\")";
    }
}