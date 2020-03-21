package promise.commons.tx;

import promise.commons.AndroidPromise;
import promise.commons.data.log.LogUtil;

class UIResponse<T, E extends Throwable> implements PromiseResult.Response<T, E> {

  private static final String TAG = LogUtil.makeTag(UIResponse.class);

  private PromiseResult.Response<? super T, ? extends E> response;
  private PromiseResult.Error<? super E> error;

  UIResponse(PromiseResult.Response<? super T, ? extends E> response,
             PromiseResult.Error<? super E> error) {
    this.response = response;
    this.error = error;
  }

  @Override
  public void onResponse(T t) throws E {
    AndroidPromise.instance().executeOnUi(() -> {
      try {
        this.response.onResponse(t);
      } catch (Throwable e) {
        if (this.error != null) this.error.onError((E) e);
        else LogUtil.e(TAG,
            new IllegalStateException("Could not process error: " + e + " ," +
                " error not provided"));
      }
    });
  }
}
