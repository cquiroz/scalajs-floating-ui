// Copyright (c) 2016-2022 Association of Universities for Research in Astronomy, Inc. (AURA)
// For license information see LICENSE or https://opensource.org/licenses/BSD-3-Clause

package floating.demo

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.vdom.TagOf
import org.scalajs.dom
import floatingui._
import floatingui.hooks._

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.html
import org.scalajs.dom
// import floatingui.raw.floatingUiReactDom.anon.Element
import floatingui.raw.floatingUiReactDomInteractions.{ typesMod => reactTypesMod }
import floatingui.raw.floatingUiCore.typesMod.Strategy
import floatingui.raw.floatingUiCore.typesMod.Placement
import floatingui.raw.floatingUiDom.anon.{ Element => DomElement }
import floatingui.raw.floatingUiReactDomInteractions.mod
import react.common._

import js.annotation._
import japgolly.scalajs.react.feature.ReactFragment
import floatingui.raw.floatingUiCore.typesMod.MiddlewareData

final case class Tooltip(trigger: VdomTag) extends ReactFnProps[Tooltip](Tooltip.component)

object Tooltip {
  type Props = Tooltip
  implicit val reuseP: Reusability[Props]          = Reusability.never
  implicit val reuseD: Reusability[Double | Null]  = Reusability.never
  implicit val reuseS: Reusability[Strategy]       = Reusability.never
  implicit val reuseA: Reusability[Placement]      = Reusability.never
  implicit val reuseM: Reusability[MiddlewareData] = Reusability.never

  val component =
    ScalaFnComponent
      .withHooks[Props]
      .useRefToAnyVdom // reference
      .useRefToAnyVdom // floating
      .useRefToVdom[dom.HTMLElement] // arrow
      .useState(false) // isOpen
      .useFloatingBy { (_, _, _, arrowRef, open) =>
        UseFloatingProps()
          // .setStrategy(Strategy.Fixed)
          .setPlacement(Placement.Top)
          .setOpen(open.value)
          .setOnOpenChange { o => println(o); open.setState(o).runNow() }
          .setWhileElementsMounted(mod.autoUpdate)
          .setMiddleware(
            js.Array(
              // autoPlacement(
              //   AutoPlacementOptions()
              //     .setAllowedPlacements(js.Array(Placement.Top, Placement.Bottom))
              // ),
              offset(5),
              flip,
              // shift(ShiftOptions().setPadding(5)),
              arrow(
                DomElement(arrowRef.raw.asInstanceOf[dom.HTMLElement])
              )
            )
          )
      }
      // .useHoverBy((_, _, _, _, h) => h.asInstanceOf[FloatingContext])
      .useInteractionsBy((_, _, _, _, _, h) =>
        List(mod.useHover(h.context.asInstanceOf[FloatingContext]))
      //      List(mod.useFocus(h.context.asInstanceOf[FloatingContext])
      // )
      )
      .useEffectWithDepsBy((_, a, b, _, _, _, _) => (a, b)) { (_, a, b, _, _, h, _) => _ =>
        // This is a way to workaround the way references are set in floatingui
        a.get.map(_.map(n => h.reference(n.asInstanceOf[dom.Element]))).void *>
          b.get.map(_.map(n => h.floating(n.asInstanceOf[dom.HTMLElement]))).void
      }
      // Memoize the style
      // .useMemoBy((props, a, b, _, _, h, _) => (h.x, h.y))((props, a, b, _, _, h, _) => _ => ())
      .render { (props, a, b, arr, open, h, _) =>
        println(s"Render ${h.x} ${h.y}")
        //   (h.strategy, h.placement.asInstanceOf[Placement], h.x, h.y, h.middlewareData)
        // ) { (_, _, _, _, _, _, _) => deps =>
        val (placementStyle, arrowStyle) =
          (h.strategy, h.placement.asInstanceOf[Placement], h.x, h.y, h.middlewareData) match {
            case (strategy, placement, x, y, middleware) =>
              val display: Map[String, String | Int] =
                if (open.value) Map.empty[String, String | Int]
                else Map[String, String | Int]("display" -> "none")

              val refStyle                                 =
                Style(
                  display ++ Map("position" -> strategy.toString,
                                 "left"     -> s"${x}px",
                                 "top"      -> s"${y}px"
                  )
                )
              val arrowOpt                                 = middleware.arrow.toOption
              val arrowStyleMap: Map[String, String | Int] =
                (arrowOpt.flatMap(_.x.toOption), arrowOpt.flatMap(_.y.toOption)) match {
                  case (Some(x), Some(y)) =>
                    Map("left" -> s"${x}px", "top" -> s"${y}px", "right" -> "", "bottom" -> "")
                  case (Some(x), None)    =>
                    Map("left" -> s"${x}px", "top" -> s"", "right" -> "", "bottom" -> "")
                  case (None, Some(y))    =>
                    Map("left" -> "", "top" -> s"${y}px", "right" -> "", "bottom" -> "")
                  case _                  => Map.empty
                }
              val arrowShift                               = "-4px"

              val placementStyle: Map[String, String | Int] = placement match {
                // case Placement.Top | Placement.TopStart | Placement.TopEnd          => Map("bottom" -> arrowShift)
                case Placement.Top    => Map("bottom" -> arrowShift)
                // case Placement.Bottom | Placement.BottomStart | Placement.BottomEnd =>
                case Placement.Bottom =>
                  Map("top" -> arrowShift)
                // case Placement.Left | Placement.LeftStart | Placement.LeftEnd       =>
                case Placement.Left   =>
                  Map("right" -> arrowShift)
                // case Placement.Right | Placement.RightStart | Placement.RightEnd    =>
                case Placement.Right  =>
                  Map("left" -> arrowShift)
                case _                => Map.empty
              }

              val arrowStyle = Style(display ++ arrowStyleMap ++ placementStyle)
              (refStyle, arrowStyle)
          }
        // }
        // .render { (props, a, b, arr, open, h, _, styles) =>
        // val (placementStyle, arrowStyle) = styles.value

        val r = props.trigger.withRef(a)
        val f = //if (open.value) {
          <.div(
            ^.cls   := "tooltip",
            ^.style := placementStyle.toJsObject,
            // ^.untypedRef := (Ref.fromJs(h.floating)),
            // ^.style := Style(
            //   Map("position" -> h.strategy.toString, "left" -> s"${h.x}px", "top" -> s"${h.y}px")
            // ).toJsObject,
            "My tooltip autoplaced 1 which is now quite a bit longer",
            <.div(^.cls := "arrow", ^.style := arrowStyle.toJsObject).withRef(arr)
            // <.div(^.id := "arrow", ^.style := Style(arrowStyle ++ placementStyle).toJsObject)
            // <.div(^.id := "arrow", ^.style := Style(arrowStyle ++ placementStyle).toJsObject)
            //   .withRef(a)
          ).withRef(b)
        //} else EmptyVdom

        ReactFragment(r, f)
      }
}
