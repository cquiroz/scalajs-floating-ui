import "./css/style.css";

import { DemoMain } from "@sjs/main.js";
DemoMain.main();
const platform = {
  getElementRects: _ref => {
    let {
      reference,
      floating,
      strategy
    } = _ref;
    return {
      reference: getRectRelativeToOffsetParent(reference, getOffsetParent(floating), strategy),
      floating: { ...getDimensions(floating),
        x: 0,
        y: 0
      }
    };
  },
  convertOffsetParentRelativeRectToViewportRelativeRect: args => convertOffsetParentRelativeRectToViewportRelativeRect(args),
  getOffsetParent: _ref2 => {
    let {
      element
    } = _ref2;
    return getOffsetParent(element);
  },
  isElement: value => isElement(value),
  getDocumentElement: _ref3 => {
    let {
      element
    } = _ref3;
    return getDocumentElement(element);
  },
  getClippingClientRect: args => getClippingClientRect(args),
  getDimensions: _ref4 => {
    let {
      element
    } = _ref4;
    return getDimensions(element);
  }
};

// console.log(platform);
const arrow = options => ({
  name: 'arrow',

  async fn(middlewareArguments) {
    // Since `element` is required, we don't Partial<> the type
    const {
      element,
      padding = 0
    } = options != null ? options : {};
    const {
      x,
      y,
      placement,
      rects,
      platform
    } = middlewareArguments;

    if (element == null) {
      if (process.env.NODE_ENV !== "production") {
        console.warn('Floating UI: No `element` was passed to the `arrow` middleware.');
      }

      return {};
    }

    const paddingObject = getSideObjectFromPadding(padding);
    const coords = {
      x,
      y
    };
    const basePlacement = getBasePlacement(placement);
    const axis = getMainAxisFromPlacement(basePlacement);
    const length = getLengthFromAxis(axis);
    const arrowDimensions = await platform.getDimensions({
      element
    });
    const minProp = axis === 'y' ? 'top' : 'left';
    const maxProp = axis === 'y' ? 'bottom' : 'right';
    const endDiff = rects.reference[length] + rects.reference[axis] - coords[axis] - rects.floating[length];
    const startDiff = coords[axis] - rects.reference[axis];
    const arrowOffsetParent = await platform.getOffsetParent({
      element
    });
    const clientSize = arrowOffsetParent ? axis === 'y' ? // @ts-ignore - fallback to 0
    arrowOffsetParent.clientHeight || 0 : // @ts-ignore - fallback to 0
    arrowOffsetParent.clientWidth || 0 : 0;
    const centerToReference = endDiff / 2 - startDiff / 2; // Make sure the arrow doesn't overflow the floating element if the center
    // point is outside of the floating element's bounds

    const min = paddingObject[minProp];
    const max = clientSize - arrowDimensions[length] - paddingObject[maxProp];
    const center = clientSize / 2 - arrowDimensions[length] / 2 + centerToReference;
    const offset = within(min, center, max);
    return {
      data: {
        [axis]: offset,
        centerOffset: center - offset
      }
    };
  }

});
if (import.meta.hot) {
  import.meta.hot.accept();
}
