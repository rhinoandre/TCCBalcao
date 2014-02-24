package tcc.balcao.view.listeners;

import tcc.balcao.model.entity.Item;

public interface CadastroProdutoListener {
	public void salvarButtonAction(Item item);
	public void limparButtonAction();
	public void cancelarButtonAction();
}
