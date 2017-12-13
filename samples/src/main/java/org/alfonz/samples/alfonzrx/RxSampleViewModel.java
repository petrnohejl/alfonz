package org.alfonz.samples.alfonzrx;

import android.databinding.ObservableField;

import org.alfonz.rx.AlfonzDisposableObserver;
import org.alfonz.rx.RxBus;
import org.alfonz.rx.RxManager;
import org.alfonz.samples.alfonzarch.BaseViewModel;
import org.alfonz.utility.DateConvertor;
import org.alfonz.utility.Logcat;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;


public class RxSampleViewModel extends BaseViewModel
{
	private static final String HELLO_CALL_TYPE = "hello";
	private static final String LOG_MESSAGE_RUN = "run: %s";
	private static final String LOG_MESSAGE_TERMINATE = "terminate all";
	private static final String LOG_MESSAGE_IS_RUNNING = "is running: %s";
	private static final String LOG_MESSAGE_ON_NEXT = "on next: %s";
	private static final String LOG_MESSAGE_ON_ERROR = "on error";
	private static final String LOG_MESSAGE_ON_COMPLETE = "on complete";
	private static final String[] NAMES = {"Peter", "James", "John", "Robert", "Michael", "William", "David", "Richard", "Charles", "Joseph", "Jane", "Mary", "Patricia", "Linda", "Barbara", "Elizabeth", "Jennifer", "Maria", "Susan", "Margaret"};

	public final ObservableField<String> log = new ObservableField<>();

	private RxManager mRxManager = new RxManager();
	private CompositeDisposable mCompositeDisposable = new CompositeDisposable();


	public RxSampleViewModel()
	{
		log.set("");
		listen();
	}


	@Override
	public void onCleared()
	{
		super.onCleared();
		mRxManager.disposeAll();
		mCompositeDisposable.clear();
	}


	public void run()
	{
		String name = getRandomName();
		log(LOG_MESSAGE_RUN, name);

		Observable<String> rawObservable = Observable.just("Hello").map(s -> String.format("%s %s!", s, name)).delay(2, TimeUnit.SECONDS);
		Observable<String> observable = mRxManager.setupObservableWithSchedulers(rawObservable, HELLO_CALL_TYPE);
		observable.subscribeWith(createHelloObserver());
	}


	public void terminate()
	{
		log(LOG_MESSAGE_TERMINATE);
		mRxManager.disposeAll();
	}


	public void isRunning()
	{
		boolean isRunning = mRxManager.isRunning(HELLO_CALL_TYPE);
		log(LOG_MESSAGE_IS_RUNNING, "" + isRunning);
	}


	private DisposableObserver<String> createHelloObserver()
	{
		return AlfonzDisposableObserver.newInstance(
				data ->
				{
					// onNext
					log(LOG_MESSAGE_ON_NEXT, data);
				},
				throwable ->
				{
					// onError
					log(LOG_MESSAGE_ON_ERROR);
				},
				() ->
				{
					// onComplete
					log(LOG_MESSAGE_ON_COMPLETE);
				}
		);
	}


	private String getRandomName()
	{
		int index = new Random().nextInt(NAMES.length);
		return (NAMES[index]);
	}


	private void log(String message)
	{
		String line = String.format("[%s] %s", DateConvertor.dateToString(new Date(), "HH:mm:ss.SSS"), message);
		log.set(line + "\n" + log.get());
	}


	private void log(String message, String arg)
	{
		log(String.format(message, arg));
	}


	private void listen()
	{
		RxBus.getInstance()
				.onEvent(Long.class)
				.doOnSubscribe(mCompositeDisposable::add)
				.subscribe(event -> Logcat.d("received " + event.toString()));
	}
}
