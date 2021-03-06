package ar.edu.unlam.eduweb


import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured(['ROLE_ALUMNO'])

@Transactional(readOnly = true)
class ComentarioController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	def temaForoService

    def index(TemaForo temaForoInstance) {
		def comentario = temaForoService.comentariosDeTema(temaForoInstance)
       
        respond comentario, model:[comentarioInstanceCount: Comentario.count(),
			 listaComentario: comentario,
			 temaForo: temaForoInstance]
    }

    def show(Comentario comentarioInstance) {
        respond comentarioInstance
    }

    def create(int temaForo) {
		def objTemaForo = TemaForo.get(temaForo)
		def usuario = Usuario.get(getAuthenticatedUser().id)
        respond new Comentario(params), model: [temaForo: objTemaForo, usuario: usuario]
    }

    @Transactional
    def save(Comentario comentarioInstance) {
        if (comentarioInstance == null) {
            notFound()
            return
        }

        if (comentarioInstance.hasErrors()) {
            respond comentarioInstance.errors, view:'create'
            return
        }

        comentarioInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'comentarioInstance.label', default: 'Comentario'), comentarioInstance.id])
                redirect comentarioInstance
            }
            '*' { respond comentarioInstance, [status: CREATED] }
        }
    }

    def edit(Comentario comentarioInstance) {
        respond comentarioInstance
    }

    @Transactional
    def update(Comentario comentarioInstance) {
        if (comentarioInstance == null) {
            notFound()
            return
        }

        if (comentarioInstance.hasErrors()) {
            respond comentarioInstance.errors, view:'edit'
            return
        }

        comentarioInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Comentario.label', default: 'Comentario'), comentarioInstance.id])
                redirect comentarioInstance
            }
            '*'{ respond comentarioInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Comentario comentarioInstance) {

        if (comentarioInstance == null) {
            notFound()
            return
        }

        comentarioInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Comentario.label', default: 'Comentario'), comentarioInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'comentarioInstance.label', default: 'Comentario'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
