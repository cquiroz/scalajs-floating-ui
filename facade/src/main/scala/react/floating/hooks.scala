// Copyright (c) 2016-2022 Association of Universities for Research in Astronomy, Inc. (AURA)
// For license information see LICENSE or https://opensource.org/licenses/BSD-3-Clause

package floatingui

import japgolly.scalajs.react._
import floatingui.raw.floatingUiReactDomInteractions.mod
// import floatingui.raw.floatingUiDom.anon.PartialShiftOptionsDetect
// import floatingui.raw.floatingUiReactDom.anon.OmitPartialComputePositio
// import floatingui.raw.floatingUiReactDomInteractions.typesMod.UseFloatingReturn
import floatingui.raw.floatingUiReactDomInteractions.{ typesMod => reactTypesMod }
import floatingui.raw.floatingUiReactDomInteractions.{ mod => interactionsMod }
import floatingui.raw.floatingUiReactDomInteractions.anon.GetFloatingProps
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.|

object HooksApiExt {
  val floatingHook =
    CustomHook[UseFloatingProps]
      .buildReturning { pos =>
        mod.useFloating[reactTypesMod.ReferenceType](pos).asInstanceOf[UseFloatingReturn]
      }

  val hoverHook =
    CustomHook[FloatingContext]
      .buildReturning { ctx =>
        interactionsMod.useHover[reactTypesMod.ReferenceType](ctx)
      }

  val interactionsHook =
    CustomHook[ElementPropsList]
      .buildReturning { ctx =>
        interactionsMod.useInteractions(ctx.toJSArray.map(_.asInstanceOf[ElementPropsItem]))
      }

  sealed class Primary[Ctx, Step <: HooksApi.AbstractStep](api: HooksApi.Primary[Ctx, Step]) {

    final def useFloating(pos: UseFloatingProps)(implicit
      step:                    Step
    ): step.Next[UseFloatingReturn] =
      useFloatingBy(_ => pos)

    final def useFloatingBy(pos: Ctx => UseFloatingProps)(implicit
      step:                      Step
    ): step.Next[UseFloatingReturn] =
      api.customBy(ctx => floatingHook(pos(ctx)))

    final def useHover(pos: FloatingContext)(implicit
      step:                 Step
    ): step.Next[ElementProps] =
      useHoverBy(_ => pos)

    final def useHoverBy(pos: Ctx => FloatingContext)(implicit
      step:                   Step
    ): step.Next[ElementProps] =
      api.customBy(ctx => hoverHook(pos(ctx)))

    final def useInteractions(pos: ElementPropsList)(implicit
      step:                        Step
    ): step.Next[GetFloatingProps] =
      useInteractionsBy(_ => pos)

    final def useInteractionsBy(pos: Ctx => ElementPropsList)(implicit
      step:                          Step
    ): step.Next[GetFloatingProps] =
      api.customBy(ctx => interactionsHook(pos(ctx)))
  }

  final class Secondary[Ctx, CtxFn[_], Step <: HooksApi.SubsequentStep[Ctx, CtxFn]](
    api: HooksApi.Secondary[Ctx, CtxFn, Step]
  ) extends Primary[Ctx, Step](api) {

    def useFloatingBy(pos: CtxFn[UseFloatingProps])(implicit
      step:                Step
    ): step.Next[UseFloatingReturn] =
      useFloatingBy(step.squash(pos)(_))

    def useHoverBy(pos: CtxFn[FloatingContext])(implicit
      step:             Step
    ): step.Next[ElementProps] =
      useHoverBy(step.squash(pos)(_))

    def useInteractionsBy(pos: CtxFn[ElementPropsList])(implicit
      step:                    Step
    ): step.Next[GetFloatingProps] =
      useInteractionsBy(step.squash(pos)(_))

  }
}

trait HooksApiExt {
  import HooksApiExt._

  implicit def hooksExtFloating1[Ctx, Step <: HooksApi.AbstractStep](
    api: HooksApi.Primary[Ctx, Step]
  ): Primary[Ctx, Step] =
    new Primary(api)

  implicit def hooksExtFloating2[Ctx, CtxFn[_], Step <: HooksApi.SubsequentStep[Ctx, CtxFn]](
    api: HooksApi.Secondary[Ctx, CtxFn, Step]
  ): Secondary[Ctx, CtxFn, Step] =
    new Secondary(api)
}

object hooks extends HooksApiExt
