package floating.demo

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom
import floating._
import floating.hooks._

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.annotation.JSExportTopLevel
import scala.scalajs.js.JSConverters._
import org.scalajs.dom.html
import react.common.style._

import js.annotation._

@JSExportTopLevel("DemoMain")
object DemoMain {
  val component =
    ScalaFnComponent
      .withHooks[Unit]
      .useMemo(())(_ => Ref[html.Div])
      .useFloatingBy { (_, ar) =>
        println("Compute")
        ComputePosition(
          Placement.Bottom,
          middleware = List(
            // flip,
            // shift,
            // offset(10),
            arrow(
              ar.value.raw //.current.asInstanceOf[js.Any]
              // Ref.toAnyVdom().get
            )              //.get.asInstanceOf[facade.React.RefHandle[html.Div]])
          )
        )
      }
      .render { (_, a, h) =>
        val refRef                          =
          Ref.fromJs(h.refs.reference.asInstanceOf[facade.React.RefHandle[html.Div | Null]])
        val floatRef                        =
          Ref.fromJs(h.refs.floating.asInstanceOf[facade.React.RefHandle[html.Div | Null]])
        // val arrowRef                        =
        //   Ref.fromJs(a.value.get.asInstanceOf[facade.React.RefHandle[html.Div | Null]])
        val arrowOffset                     = h.middlewareData.arrow.toOption
        println("----")
        org.scalajs.dom.console.log(h)
        println(h.middlewareData)
        println(a.value.raw)
        println(a.value.raw.current)
        println(arrowOffset)
        println(arrowOffset.map(_.x))
        val arrowStyle: Map[String, String] =
          (arrowOffset.flatMap(_.x.toOption), arrowOffset.flatMap(_.y.toOption)) match {
            case (Some(x), Some(y)) =>
              Map("left" -> s"${x}px", "top" -> s"${y}px")
            case _                  => Map.empty
          }
        println(arrowStyle)
        val r                               = <.button(^.untypedRef := refRef, "Example")
        val f                               = <.div(
          ^.id         := "tooltip",
          ^.untypedRef := floatRef,
          ^.style      := Style(Map("left" -> s"${h.x}px", "top" -> s"${h.y}px")).toJsObject,
          "My tooltip",
          <.div(^.id    := "arrow",
                // ^.untypedRef := a.value.raw,
                ^.style := Style(arrowStyle).toJsObject
          )
            .withRef(a.value)
        )
        React.Fragment(r, f)
      }

  @JSExport
  def main(): Unit = {

    val container = Option(dom.document.getElementById("root")).getOrElse {
      val elem = dom.document.createElement("div")
      elem.id = "root"
      dom.document.body.appendChild(elem)
      elem
    }

    component().renderIntoDOM(container)
    ()
  }
}
