package ar.edu.unlam.eduweb

import ar.edu.unlam.eduweb.CursoUsuario
import ar.edu.unlam.eduweb.Rol
import ar.edu.unlam.eduweb.UsuarioRol
import ar.edu.unlam.eduweb.Usuario


import grails.transaction.Transactional

@Transactional
class CursoService {

    def cursosDeUsuario(Usuario usuario) {
		return CursoUsuario.findAllByUsuario(usuario)

    }
	
	def profesorDeCurso(Curso cursoInstance) {
		//1. Buscamos el registro ROLE_PROFESOR
		def rolProfesor = Rol.findAllByAuthority('ROLE_PROFESOR')
		
		//2. Buscamos a todos los usuarios con ROLE_PROFESOR
		def listaProfesores = UsuarioRol.findAllByRol(rolProfesor)
		
		//3. Buscamos el objeto cursoUsuario que corresponda al Curso actual y que contenga un Profesor de la lista de profesores
		def cursoUsuario = CursoUsuario.findByCursoAndUsuarioInList(cursoInstance, listaProfesores.usuario)
		//def cursoUsuario = CursoUsuario.findAll("FROM CursoUsuario AS cu WHERE cu.curso = :curso AND cu.usuario IN :listaProfesores", [curso: cursoInstance, listaProfesores: listaProfesores.usuario])
		
		//4. Obtenemos los datos del usuario Profesor
		def profesor = Usuario.get(cursoUsuario.usuario.id)
		profesor.getAuthority()
		
		return profesor
	}
}