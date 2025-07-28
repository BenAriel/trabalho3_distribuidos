package gateway.api.dto;

public class CompraDTO {
    private String id;
    private String idUsuario;
    private String idProduto;
    private int quantidade;
    private String ordemDeCompra;

    public CompraDTO(String id, String idUsuario, String idProduto, int quantidade, String ordemDeCompra) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.ordemDeCompra = ordemDeCompra;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getOrdemDeCompra() {
        return ordemDeCompra;
    }

    public void setOrdemDeCompra(String ordemDeCompra) {
        this.ordemDeCompra = ordemDeCompra;
    }

    @Override
    public String toString() {
        return "CompraDTO{" +
                "id='" + id + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                ", idProduto='" + idProduto + '\'' +
                ", quantidade=" + quantidade +
                ", ordemDeCompra='" + ordemDeCompra + '\'' +
                '}';
    }
}

