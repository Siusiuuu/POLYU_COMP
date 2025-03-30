package hk.edu.polyu.comp.comp2021.cvfs.model;

abstract class Criterion {
    public abstract boolean evaluate(File file);
}

class SimpleCriterion extends Criterion {
    private final String criName;
    private final String attrName;
    private final String op;
    private final Object val;

    public SimpleCriterion(String criName, String attrName, String op, Object val) {
        this.criName = criName;
        this.attrName = attrName;
        this.op = op;
        this.val = val;
    }

    @Override
    public boolean evaluate(File file) {
        switch (attrName) {
            case "name":
                return op.equals("contains") && file.getName().contains((String) val);
            case "type":
                return op.equals("equals") && file.getType().equals(val);
            case "size":
                int fileSize = file.getSize();
                int intValue = (Integer) val;
                switch (op) {
                    case ">":
                        return fileSize > intValue;
                    case "<":
                        return fileSize < intValue;
                    case ">=":
                        return fileSize >= intValue;
                    case "<=":
                        return fileSize <= intValue;
                    case "==":
                        return fileSize == intValue;
                    case "!=":
                        return fileSize != intValue;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }

    public String getAttrName() {
        return attrName;
    }

    public String getOp() {
        return op;
    }

    public Object getVal() {
        return val;
    }
}

class IsDocumentCriterion extends Criterion {
    @Override
    public boolean evaluate(File file) {
        return file.isDocument();
    }
}

class NegationCriterion extends Criterion {
    private final Criterion criterion;

    public NegationCriterion(Criterion criterion) {
        this.criterion = criterion;
    }

    @Override
    public boolean evaluate(File file) {
        return !criterion.evaluate(file);
    }
}

class BinaryCriterion extends Criterion {
    private final Criterion left;
    private final Criterion right;
    private final String logicOp;

    public BinaryCriterion(Criterion left, String logicOp, Criterion right) {
        this.left = left;
        this.right = right;
        this.logicOp = logicOp;
    }

    @Override
    public boolean evaluate(File file) {
        return logicOp.equals("&&") ? left.evaluate(file) && right.evaluate(file) :
                logicOp.equals("||") && (left.evaluate(file) || right.evaluate(file));
    }

    public String getLogicOp() {
        return logicOp;
    }
}