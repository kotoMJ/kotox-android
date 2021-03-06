package cz.kotox.core.databinding;

// this is in Java so that it can be used in Data Binding together with lambda functionality in kotlin
public interface OnItemValueChangeListener<T, U> {
	void onValueChange(T item, U value);
}
