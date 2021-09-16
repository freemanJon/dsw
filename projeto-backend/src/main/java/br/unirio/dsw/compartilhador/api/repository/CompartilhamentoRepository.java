package br.unirio.dsw.compartilhador.api.repository;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.unirio.dsw.compartilhador.api.model.Compartilhamento;
import br.unirio.dsw.compartilhador.api.model.ItemCompartilhado;

@Transactional(readOnly = true)
public interface CompartilhamentoRepository extends JpaRepository<Compartilhamento, Long>
{
	List<Compartilhamento> findByUsuarioId(Long usuarioId);
	
	Page<Compartilhamento> findByUsuarioId(Long usuarioId, Pageable pageable);

	List<Compartilhamento> findByUsuarioIdAndAceito(Long usuarioId, boolean aceito);
	
	@Query("SELECT count(*) FROM Compartilhamento c WHERE c.aceito = 0 AND c.rejeitado = 0 AND c.canceladoDono = 0 AND c.canceladoUsuario = 0") 
	long contarCompartilhamentosAbertos();
}