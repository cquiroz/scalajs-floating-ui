// Copyright (c) 2016-2022 Association of Universities for Research in Astronomy, Inc. (AURA)
// For license information see LICENSE or https://opensource.org/licenses/BSD-3-Clause

package floating

import japgolly.scalajs.react._
import floatingui.raw.floatingUiReactDom.mod
import floatingui.raw.floatingUiReactDom.anon.OmitPartialComputePositio
import floatingui.raw.floatingUiReactDom.srcMod.UseFloatingReturn

object HooksApiExt {
  val floatingHook =
    CustomHook[ComputePosition]
      .buildReturning { pos =>
        mod.useFloating(pos)
      }

  sealed class Primary[Ctx, Step <: HooksApi.AbstractStep](api: HooksApi.Primary[Ctx, Step]) {

    final def useFloating(pos: ComputePosition)(implicit
      step:                    Step
    ): step.Next[UseFloatingReturn] =
      useFloatingBy(_ => pos)

    final def useFloatingBy(pos: Ctx => ComputePosition)(implicit
      step:                      Step
    ): step.Next[UseFloatingReturn] =
      api.customBy(ctx => floatingHook(pos(ctx)))
  }

  final class Secondary[Ctx, CtxFn[_], Step <: HooksApi.SubsequentStep[Ctx, CtxFn]](
    api: HooksApi.Secondary[Ctx, CtxFn, Step]
  ) extends Primary[Ctx, Step](api) {

    def useFloatingBy(pos: CtxFn[ComputePosition])(implicit
      step:                Step
    ): step.Next[UseFloatingReturn] =
      useFloatingBy(step.squash(pos)(_))
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
