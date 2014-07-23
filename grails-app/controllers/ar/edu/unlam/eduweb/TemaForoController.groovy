package ar.edu.unlam.eduweb

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured(['ROLE_ALUMNO'])
@Transactional(readOnly = true)
class TemaForoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	def temaForoService


    def index(Curso cursoInstance) {
       
		def temasForo= temaForoService.temasAbiertosDeCurso(cursoInstance)
	    respond temasForo, model:[temaForoInstanceCount: TemaForo.count(),
			 listaForo:temasForo]
    }

    def show(TemaForo temaForoInstance) {
        respond temaForoInstance
    }

    def create() {
        respond new TemaForo(params)
    }

    @Transactional
    def save(TemaForo temaForoInstance) {
        if (temaForoInstance == null) {
            notFound()
            return
        }

        if (temaForoInstance.hasErrors()) {
            respond temaForoInstance.errors, view:'create'
            return
        }

        temaForoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'temaForoInstance.label', default: 'TemaForo'), temaForoInstance.id])
                redirect temaForoInstance
            }
            '*' { respond temaForoInstance, [status: CREATED] }
        }
    }

    def edit(TemaForo temaForoInstance) {
        respond temaForoInstance
    }

    @Transactional
    def update(TemaForo temaForoInstance) {
        if (temaForoInstance == null) {
            notFound()
            return
        }

        if (temaForoInstance.hasErrors()) {
            respond temaForoInstance.errors, view:'edit'
            return
        }

        temaForoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'TemaForo.label', default: 'TemaForo'), temaForoInstance.id])
                redirect temaForoInstance
            }
            '*'{ respond temaForoInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(TemaForo temaForoInstance) {

        if (temaForoInstance == null) {
            notFound()
            return
        }

        temaForoInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'TemaForo.label', default: 'TemaForo'), temaForoInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'temaForoInstance.label', default: 'TemaForo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
