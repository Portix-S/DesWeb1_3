// "locadora" Referencia à pasta locadora, no templates
// "locadoras" Referencia o próprio LocadoraController

package br.ufscar.dc.dsw.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Locadora;
import br.ufscar.dc.dsw.domain.Locacao;
import br.ufscar.dc.dsw.service.spec.ILocadoraService;
import br.ufscar.dc.dsw.service.spec.ILocacaoService;

@Controller
@RequestMapping("/locadoras")
public class LocadoraController {

	@Autowired
	private ILocadoraService locadoraService;

	@Autowired
	private ILocacaoService locacaoService;

	@GetMapping("/cadastrar")
	public String cadastrar(Locadora locadora, ModelMap model) {
		System.out.println("Cadastrando nova Locadora");
		model.addAttribute("Locadora", locadora);
		return "locadora/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("locadoras", locadoraService.buscarTodos());
		return "locadora/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Locadora locadora, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "locadora/cadastro";
		}

		locadoraService.salvar(locadora);
		attr.addFlashAttribute("sucess", "Locadora inserida com sucesso");
		return "redirect:/locadoras/listar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("locadora", locadoraService.buscarPorId(id));
		return "locadora/cadastro";
	}

	@GetMapping("/procurarPorCNPJ/{CNPJ}")
	public String buscarPorCNPJ(@PathVariable("CNPJ") String CNPJ, ModelMap model) {
		model.addAttribute("locadora", locadoraService.buscarPorCNPJ(CNPJ));
		return "locadora/cadastro"; //Verificar pra onde isso retornaria de fato
	}

	@GetMapping("/procurarPorTelefone/{telefone}")
	public String buscarPorTelefone(@PathVariable("telefone") String telefone, ModelMap model) {
		model.addAttribute("locadora", locadoraService.buscarPorTelefone(telefone));
		return "locadora/cadastro"; //Verificar pra onde isso retornaria de fato
	}

	@PostMapping("/editar")
	public String editar(@Valid Locadora locadora, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "locadora/cadastro";
		}

		locadoraService.salvar(locadora);
		attr.addFlashAttribute("sucess", "Locadora editada com sucesso.");
		return "redirect:/locadoras/listar";
	}

	@GetMapping("/excluirPorId/{id}") //Mudar no html para excluir por id
	public String excluirPorID(@PathVariable("id") Long id, RedirectAttributes attr) {
		locadoraService.excluirPorId(id);
		attr.addFlashAttribute("sucess", "Locadora excluída com sucesso.");
		return "redirect:/locadoras/listar";
	}

	@GetMapping("/excluirPorCNPJ/{CNPJ}") 
	public String excluirPorCNPJ(@PathVariable("CNPJ") String CNPJ, RedirectAttributes attr) {
		locadoraService.excluirPorCNPJ(CNPJ);
		attr.addFlashAttribute("sucess", "Locadora excluída com sucesso.");
		return "redirect:/locadoras/listar";
	}

	@ModelAttribute("locacoes") // Alguém entendeu a utilidade disso? Porque eu gostaria de printar todas as editoras por aqui?
	public List<Locacao> listaLocacoes() {
		return locacaoService.buscarTodos();
	}
}