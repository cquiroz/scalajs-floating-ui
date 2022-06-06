// Copyright (c) 2016-2022 Association of Universities for Research in Astronomy, Inc. (AURA)
// For license information see LICENSE or https://opensource.org/licenses/BSD-3-Clause

package floating.demo

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom
import floatingui._
import floatingui.hooks._

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.html
import react.common.style._

import js.annotation._

@JSExportTopLevel("DemoMain")
object DemoMain {
  // val component =
  //   ScalaFnComponent
  //     .withHooks[Unit]
  //     .useRefToAnyVdom
  //     .useFloatingBy { (_, ar) =>
  //       ComputePosition(
  //         Placement.Top,
  //         middleware = List(
  //           offset(10),
  //           flip,
  //           shift(ShiftOptions().setPadding(5)),
  //           arrow(
  //             Element(ar.raw.asInstanceOf[js.Any])
  //           )
  //         )
  //       )
  //     }
  //     .render { (_, a, h) =>
  //       org.scalajs.dom.window.console.log(h)
  //       val refRef    =
  //         Ref.fromJs(h.refs.reference.asInstanceOf[facade.React.RefHandle[html.Div | Null]])
  //       val floatRef  =
  //         Ref.fromJs(h.refs.floating.asInstanceOf[facade.React.RefHandle[html.Div | Null]])
  //       val arrowOpt  = h.middlewareData.arrow.toOption
  //       val placement = h.placement
  //
  //       val arrowStyle: Map[String, String | Int] =
  //         (arrowOpt.flatMap(_.x.toOption), arrowOpt.flatMap(_.y.toOption)) match {
  //           case (Some(x), Some(y)) =>
  //             Map("left" -> s"${x}px", "top" -> s"${y}px", "right" -> "", "bottom" -> "")
  //           case (Some(x), None)    =>
  //             Map("left" -> s"${x}px", "top" -> s"", "right" -> "", "bottom" -> "")
  //           case (None, Some(y))    =>
  //             Map("left" -> "", "top" -> s"${y}px", "right" -> "", "bottom" -> "")
  //           case _                  => Map.empty
  //         }
  //
  //       val arrowShift = "-4px"
  //
  //       val placementStyle: Map[String, String | Int] = placement match {
  //         case Placement.Top | Placement.TopStart | Placement.TopEnd          => Map("bottom" -> arrowShift)
  //         case Placement.Bottom | Placement.BottomStart | Placement.BottomEnd =>
  //           Map("top" -> arrowShift)
  //         case Placement.Left | Placement.LeftStart | Placement.LeftEnd       =>
  //           Map("right" -> arrowShift)
  //         case Placement.Right | Placement.RightStart | Placement.RightEnd    =>
  //           Map("left" -> arrowShift)
  //         case _                                                              => Map.empty
  //       }
  //
  //       val r = <.button(^.untypedRef := refRef, "Example")
  //       val f = <.div(
  //         ^.id         := "tooltip",
  //         ^.untypedRef := floatRef,
  //         ^.style      := Style(Map("left" -> s"${h.x}px", "top" -> s"${h.y}px")).toJsObject,
  //         "My tooltip which is now quite a bit longer",
  //         <.div(^.id := "arrow", ^.style := Style(arrowStyle ++ placementStyle).toJsObject)
  //           .withRef(a)
  //       )
  //       React.Fragment(r, f)
  //     }

  @JSExport
  def main(): Unit = {

    val container = Option(dom.document.getElementById("root")).getOrElse {
      val elem = dom.document.createElement("div")
      elem.id = "root"
      dom.document.body.appendChild(elem)
      elem
    }

    Tooltip(<.button("Example")).renderIntoDOM(container)
    // component().renderIntoDOM(container)
    ()
  }
}
