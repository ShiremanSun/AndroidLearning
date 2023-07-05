package com.sunny.student.retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.intrinsics.startCoroutineCancellable
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.*

class RetrofitViewModel : ViewModel() {

    fun get() {
        GlobalScope.launch {
            val result = withContext(Dispatchers.IO) {
                "result"
            }
            result.length
        }
    }


    suspend fun <T>doSomething(block: (Continuation<T>?) -> Any?) {
        block(null)
    }

   /* @InternalCoroutinesApi
    public suspend fun <T> withContext(
        context: CoroutineContext,
        block: suspend CoroutineScope.() -> T
    ): T = suspendCoroutineUninterceptedOrReturn sc@{ uCont ->
        // compute new context
        val oldContext = uCont.context
        val newContext = oldContext + context
        // always check for cancellation of new context
        newContext.checkCompletion()
        // FAST PATH #1 -- new context is the same as the old one
        if (newContext === oldContext) {
            val coroutine = ScopeCoroutine(newContext, uCont) // MODE_DIRECT
            return@sc coroutine.startUndispatchedOrReturn(coroutine, block)
        }

        // SLOW PATH -- use new dispatcher
        val coroutine = DispatchedCoroutine(newContext, uCont) // MODE_CANCELLABLE
        coroutine.initParentJob()
        block.startCoroutineCancellable(coroutine, coroutine)
        coroutine.getResult()
    }*/

}
//internal fun <R, T> (suspend (R) -> T).startCoroutineCancellable(receiver: R, completion: Continuation<T>)  = completion



/*
@InternalCoroutinesApi
fun CoroutineContext.checkCompletion() {
    val job = get(Job)
    if (job != null && !job.isActive) throw job.getCancellationException()
}

@InternalCoroutinesApi
internal open class ScopeCoroutine<in T>(
    context: CoroutineContext,
    @JvmField val uCont: Continuation<T> // unintercepted continuation
) : AbstractCoroutine<T>(context, true), CoroutineStackFrame {
    final override val callerFrame: CoroutineStackFrame? get() = uCont as CoroutineStackFrame?
    final override fun getStackTraceElement(): StackTraceElement? = null
    final override val isScopedCoroutine: Boolean get() = true

}
// Used by withContext when context dispatcher changes
@InternalCoroutinesApi
private class DispatchedCoroutine<in T>(
    context: CoroutineContext,
    uCont: Continuation<T>
) : ScopeCoroutine<T>(context, uCont) {
     fun initParentJob() {
    }

    fun getResult() {

    }
}


internal class ContextScope(context: CoroutineContext) : CoroutineScope {
    override val coroutineContext: CoroutineContext = context
}
internal typealias CoroutineStackFrame = kotlin.coroutines.jvm.internal.CoroutineStackFrame
@PublishedApi internal const val MODE_ATOMIC_DEFAULT = 0 // schedule non-cancellable dispatch for suspendCoroutine
@PublishedApi internal const val MODE_CANCELLABLE = 1    // schedule cancellable dispatch for suspendCancellableCoroutine
@PublishedApi internal const val MODE_DIRECT = 2         // when the context is right just invoke the delegate continuation direct
@PublishedApi internal const val MODE_UNDISPATCHED = 3   // when the thread is right, but need to mark it with current coroutine
@PublishedApi internal const val MODE_IGNORE = 4         // don't do anything

@InternalCoroutinesApi
internal fun <T, R> AbstractCoroutine<T>.startUndispatchedOrReturn(receiver: R, block: suspend R.() -> T): Any? {
    return block.startCoroutineUninterceptedOrReturn(receiver, this)

}
*/


suspend fun <T>test(context: CoroutineContext,
         block: suspend CoroutineScope.() -> T){

}

