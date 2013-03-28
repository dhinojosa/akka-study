package akkastudy.askactor.java;

import akka.dispatch.OnSuccess;

public class OnSuccessHandler<T> extends OnSuccess<T> {
    @Override
    public void onSuccess(T result) throws Throwable {
        System.out.println(result);
    }
}
