package promise.commons.model;

import promise.commons.Promise;
import promise.commons.data.log.LogUtil;

class UIResponse<T, E extends Throwable> implements Result.Response<T, E> {

  private static final String TAG = LogUtil.makeTag(UIResponse.class);

  private Result.Response<? super T, ? extends E> response;
  private Result.Error<? super E> error;

  UIResponse(Result.Response<? super T, ? extends E> response,
             Result.Error<? super E> error) {
    this.response = response;
    this.error = error;
  }

  @Override
  public void onResponse(T t) throws E {
    Promise.instance().executeOnUi(() -> {
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
