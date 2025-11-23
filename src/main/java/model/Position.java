package model;

public enum Position {
    GOALKEEPER,
    DEFENDER,
    MIDFIELD,
    FORWARD;

    public String translateToRussian() {
        return switch (this) {
            case GOALKEEPER -> "Вратарь";
            case DEFENDER -> "Защитник";
            case MIDFIELD -> "Полузащитник";
            case FORWARD -> "Нападающий";
        };
    }
}
