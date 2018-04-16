package solutions.empire.wallyces.dbParseTable

enum class SugestaoTableEnum(coluna: String) {

    OBJECT_ID("objectId"),
    ACL("ACL"),
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt"),
    TITULO("titulo"),
    MODELO_CELULAR("modelo_celular"),
    DESCRICAO("descricao"),
    LAYOUT("check_layout"),
    PERFOMANCE("check_perfomance"),
    DISPONIBILIDADE("check_disponibilidade"),
    INCONSISTENCIA("check_inconsistencia")
}