package solutions.empire.wallyces.dbParseTable

enum class ProfessorTableEnum(coluna: String) {

    TABELA_PROFESSO("professor"),
    OBJECT_ID("objectId"),
    ACL("ACL"),
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt"),
    NOME("nome"),
    EMAIL("email"),
    CURSO("curso"),
    PERMISSAO("permissao")
}