import floatingui.raw.floatingUiCore.typesMod
import floatingui.raw.floatingUiReactDom.anon.OmitPartialComputePositio
import floatingui.raw.floatingUiCore.mod
import floatingui.raw.floatingUiCore.arrowMod
import japgolly.scalajs.react._
import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.|
import org.scalajs.dom.html
import org.w3c.dom.html.HTMLElement

package object floating {

  implicit def Compute2Omni(c: floating.ComputePosition): OmitPartialComputePositio = {
    val p: OmitPartialComputePositio = (new js.Object()).asInstanceOf[OmitPartialComputePositio]
    p.placement = c.placement
    p.strategy = c.strategy
    p.middleware = c.middleware.toJSArray
    p
  }

  lazy val flip        = mod.flip()
  def offset(i: Int)   = mod.offset(i)
  lazy val shift       = mod.shift()
  def arrow(i: js.Any) = mod.arrow(arrowMod.Options(i))
  // def arrow(i: HTMLElement) = mod.arrow(i)

  object Placement {
    lazy val Top         = typesMod.Placement.top
    lazy val Right       = typesMod.Placement.right
    lazy val Bottom      = typesMod.Placement.bottom
    lazy val Left        = typesMod.Placement.left
    lazy val TopStart    = typesMod.Placement.`top-start`
    lazy val TopEnd      = typesMod.Placement.`top-end`
    lazy val RightStart  = typesMod.Placement.`right-start`
    lazy val RightEnd    = typesMod.Placement.`right-end`
    lazy val BottomStart = typesMod.Placement.`bottom-start`
    lazy val BottomEnd   = typesMod.Placement.`bottom-end`
    lazy val LeftStart   = typesMod.Placement.`left-start`
    lazy val LeftEnd     = typesMod.Placement.`left-end`
  }
}

package floating {
  final case class ComputePosition(
    placement:  js.UndefOr[typesMod.Placement] = js.undefined,
    strategy:   js.UndefOr[typesMod.Strategy] = js.undefined,
    middleware: List[typesMod.Middleware] = Nil
  )

}
