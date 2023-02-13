import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigCalcVisitorImpl extends BigCalcBaseVisitor<BigDecimal> {

    @Override
    public BigDecimal visitExpressionStatement(BigCalcParser.ExpressionStatementContext ctx) {
	return visit(ctx.expression());
    }

    @Override
    public BigDecimal visitMulDiv(BigCalcParser.MulDivContext ctx) {
        final BigDecimal left = visit(ctx.expression(0));
        final BigDecimal right = visit(ctx.expression(1));
        if (ctx.op.getText().equals("*")) {
            return left.multiply(right);
        } else {
            return left.divide(right, 10, RoundingMode.HALF_UP);
        }
    }

    @Override
    public BigDecimal visitAddSub(BigCalcParser.AddSubContext ctx) {
        final BigDecimal left = visit(ctx.expression(0));
        final BigDecimal right = visit(ctx.expression(1));
        if (ctx.op.getText().equals("+")) {
            return left.add(right);
        } else {
            return left.subtract(right);
        }
    }

    @Override
    public BigDecimal visitNum(BigCalcParser.NumContext ctx) {
        return new BigDecimal(ctx.Number().getText());
    }

}
