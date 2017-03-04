/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.apex.ast;

import java.lang.reflect.Field;

import apex.jorje.data.ast.Identifier;
import apex.jorje.semantic.ast.compilation.UserInterface;
import net.sourceforge.pmd.Rule;

public class ASTUserInterface extends ApexRootNode<UserInterface> implements CanSuppressWarnings {

    public ASTUserInterface(UserInterface userInterface) {
        super(userInterface);
    }

    public Object jjtAccept(ApexParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    @Override
    public String getImage() {
        try {
            Field field = node.getClass().getDeclaredField("name");
            field.setAccessible(true);
            Identifier name = (Identifier) field.get(node);
            return name.value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.getImage();
    }

    public boolean hasSuppressWarningsAnnotationFor(Rule rule) {
        for (int i = 0; i < jjtGetNumChildren(); i++) {
            if (jjtGetChild(i) instanceof ASTAnnotation) {
                ASTAnnotation a = (ASTAnnotation) jjtGetChild(i);
                if (a.suppresses(rule)) {
                    return true;
                }
            }
        }
        return false;
    }
}
