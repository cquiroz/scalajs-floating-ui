// Copyright (c) 2016-2022 Association of Universities for Research in Astronomy, Inc. (AURA)
// For license information see LICENSE or https://opensource.org/licenses/BSD-3-Clause

package floating.demo

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.vdom.TagOf
import org.scalajs.dom
import floating._
import floating.hooks._

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.html
import org.scalajs.dom
import floatingui.raw.floatingUiReactDom.anon.Element
import react.common._

import js.annotation._

final case class Tooltip(trigger: VdomTag) extends ReactFnProps[Tooltip](Tooltip.component)

object Tooltip {
  type Props = Tooltip

  val component =
    ScalaFnComponent
      .withHooks[Props]
      .useRefToAnyVdom
      .useFloatingBy { (_, ar) =>
        ComputePosition(
          Placement.Top,
          middleware = List(
            offset(10),
            flip,
            shift(ShiftOptions().setPadding(5)),
            arrow(
              Element(ar.raw.asInstanceOf[js.Any])
            )
          )
        )
      }
      .useState(false)
      .render { (props, a, h, open) =>
        org.scalajs.dom.window.console.log(h)
        val refRef    =
          Ref.fromJs(h.refs.reference.asInstanceOf[facade.React.RefHandle[dom.Node | Null]])
        val floatRef  =
          Ref.fromJs(h.refs.floating.asInstanceOf[facade.React.RefHandle[html.Div | Null]])
        val arrowOpt  = h.middlewareData.arrow.toOption
        val placement = h.placement

        val arrowStyle: Map[String, String | Int] =
          (arrowOpt.flatMap(_.x.toOption), arrowOpt.flatMap(_.y.toOption)) match {
            case (Some(x), Some(y)) =>
              Map("left" -> s"${x}px", "top" -> s"${y}px", "right" -> "", "bottom" -> "")
            case (Some(x), None)    =>
              Map("left" -> s"${x}px", "top" -> s"", "right" -> "", "bottom" -> "")
            case (None, Some(y))    =>
              Map("left" -> "", "top" -> s"${y}px", "right" -> "", "bottom" -> "")
            case _                  => Map.empty
          }

        val arrowShift = "-4px"

        val placementStyle: Map[String, String | Int] = placement match {
          case Placement.Top | Placement.TopStart | Placement.TopEnd          => Map("bottom" -> arrowShift)
          case Placement.Bottom | Placement.BottomStart | Placement.BottomEnd =>
            Map("top" -> arrowShift)
          case Placement.Left | Placement.LeftStart | Placement.LeftEnd       =>
            Map("right" -> arrowShift)
          case Placement.Right | Placement.RightStart | Placement.RightEnd    =>
            Map("left" -> arrowShift)
          case _                                                              => Map.empty
        }

        val r = props.trigger(^.onClick --> open.setState(true)).withRef(refRef)
        val f = if (open.value) {
          <.div(
            ^.id         := "tooltip",
            ^.untypedRef := floatRef,
            ^.style      := Style(Map("left" -> s"${h.x}px", "top" -> s"${h.y}px")).toJsObject,
            "My tooltip which is now quite a bit longer",
            <.div(^.id := "arrow", ^.style := Style(arrowStyle ++ placementStyle).toJsObject)
              .withRef(a)
          )
        } else EmptyVdom

        React.Fragment(r, f)
      }
}
