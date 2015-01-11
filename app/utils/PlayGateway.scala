package utils

import controllers.routes
import play.api.mvc.Controller

object PlayGateway extends Controller {

  def versioned(path: String) = routes.Assets.versioned(path).url

}
