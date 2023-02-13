/**
 * @author Lamies Abbas
 * @id 12128050
 */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.HashMap;

public class BigCalcProgVisitorImpl extends BigCalcProgBaseVisitor<BigDecimal> {
    private final Map<String, BigDecimal> variables = new HashMap<>();

    @Override
    public BigDecimal visitExpressionStatement(BigCalcProgParser.ExpressionStatementContext ctx) {
        for(int i = 0; i < ctx.statement().size()-1; i++){
            visit(ctx.statement(i));
        }
        return visit(ctx.statement(ctx.statement().size()-1));
    }

    @Override
    public BigDecimal visitMulDiv(BigCalcProgParser.MulDivContext ctx) {
        final BigDecimal left = visit(ctx.expression(0));
        final BigDecimal right = visit(ctx.expression(1));
        if (ctx.op.getText().equals("*")) {
            return left.multiply(right);
        } else {
            return left.divide(right, 10, RoundingMode.HALF_UP);
        }
    }

    @Override
    public BigDecimal visitCondition(BigCalcProgParser.ConditionContext ctx) {
        final BigDecimal left = visit(ctx.expression());
        final BigDecimal middle = visit(ctx.statement(0));

        if (left.doubleValue() != 0.0) {
           // visit(ctx.statement(0));
           // return new BigDecimal(middle);
           return middle;
        } else {
             return visit(ctx.statement(1));
        }
    }

    @Override
    public BigDecimal visitAddSub(BigCalcProgParser.AddSubContext ctx) {
        final BigDecimal left = visit(ctx.expression(0));
        final BigDecimal right = visit(ctx.expression(1));
        if (ctx.op.getText().equals("+")) {
            return left.add(right);
        } else {
            return left.subtract(right);
        }
    }

    @Override
    public BigDecimal visitNum(BigCalcProgParser.NumContext ctx) {
        return new BigDecimal(ctx.Number().getText());
    }

    @Override
    public BigDecimal visitAssignExp(BigCalcProgParser.AssignExpContext ctx) {
        this.variables.put(ctx.VAR().getText(), visit(ctx.expression()));
        return variables.get(ctx.VAR().getText());
    }

    @Override
    public BigDecimal visitAssignNum(BigCalcProgParser.AssignNumContext ctx) {
        this.variables.put(ctx.VAR().getText(), new BigDecimal(ctx.Number().getText()));
        return variables.get(ctx.VAR().getText());
    }

    @Override
    public BigDecimal visitVar(BigCalcProgParser.VarContext ctx){
        final BigDecimal value = variables.get(ctx.VAR().getText());
        if(value != null){
            return value;
        }
        System.out.println("Warning: undefined variable: " + ctx.VAR().getText());
        return new BigDecimal(0);
    }

    @Override
    public BigDecimal visitParentheses(BigCalcProgParser.ParenthesesContext ctx){
        return visit(ctx.expression());
    }
}
