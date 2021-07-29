package com.project.inventory.exception;

import com.project.inventory.exception.apiError.ApiError;
import com.project.inventory.exception.invalid.InvalidException;
import com.project.inventory.exception.invalid.cart.CartItemNotValidException;
import com.project.inventory.exception.invalid.order.OrderInvalidException;
import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.exception.notFound.cart.CartItemNotFoundException;
import com.project.inventory.exception.notFound.cart.CartNotFound;
import com.project.inventory.exception.notFound.customer.CustomerAddressNotFoundException;
import com.project.inventory.exception.notFound.order.OrderItemNotFoundException;
import com.project.inventory.exception.notFound.paymentMethod.PaymentMethodNotFoundException;
import com.project.inventory.exception.notFound.product.ProductNotFound;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Date;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomGlobalException extends ResponseEntityExceptionHandler {

//     handle those specific exceptions
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> handleAccountNotFoundException(AccountNotFoundException accountNotFoundException,
                                                            WebRequest request) {
        ApiError apiError = new ApiError(new Date(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                accountNotFoundException.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartNotFound.class)
    public ResponseEntity<?> handleCartNotFoundException(CartNotFound cartNotFound,
                                                         WebRequest request) {
        ApiError apiError = new ApiError(new Date(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                cartNotFound.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<?> handleCartItemNotFoundException(CartItemNotFoundException cartItemNotFoundException,
                                                             WebRequest request) {
        ApiError apiError = new ApiError(new Date(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                cartItemNotFoundException.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartItemNotValidException.class)
    public ResponseEntity<?> handleCartItemNotValidException(CartItemNotValidException cartItemNotValidException,
                                                             WebRequest request) {
        ApiError apiError = new ApiError(new Date(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                cartItemNotValidException.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerAddressNotFoundException.class)
    public ResponseEntity<?> handleCustomerAddressNotFoundException(CustomerAddressNotFoundException customerAddressNotFoundException,
                                                                    WebRequest request) {
        ApiError apiError = new ApiError(new Date(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                customerAddressNotFoundException.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaymentMethodNotFoundException.class)
    public ResponseEntity<?> handlePaymentMethodNotFoundException(PaymentMethodNotFoundException paymentMethodNotFoundException,
                                                                  WebRequest request) {
        ApiError apiError = new ApiError(new Date(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                paymentMethodNotFoundException.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<?> handleProductNotFoundException(ProductNotFound productNotFound,
                                                            WebRequest request) {
        ApiError apiError = new ApiError(new Date(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                productNotFound.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderInvalidException.class)
    public ResponseEntity<?> handleShoppingOrderInvalidException(OrderInvalidException orderInvalidException,
                                                                 WebRequest request) {
        ApiError apiError = new ApiError(new Date(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                orderInvalidException.getMessage(),
                request.getDescription(true));
        return new ResponseEntity(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderItemNotFoundException.class)
    public ResponseEntity<?> handleOrderItemNotFoundException(OrderItemNotFoundException orderItemNotFoundException,
                                                              WebRequest request) {
        ApiError apiError = new ApiError(new Date(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                orderItemNotFoundException.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<?> handleInvalidException(InvalidException invalidException,
                                                    WebRequest request) {
        ApiError apiError = new ApiError(new Date(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                invalidException.getMessage(),
                request.getDescription(true));
        return new ResponseEntity(apiError, HttpStatus.BAD_REQUEST);
    }
    // handle global exceptions

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalExceptions(Exception exception,
                                                    WebRequest request) {
        ApiError apiError = new ApiError(new Date(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException notFoundException,
                                                     WebRequest request) {
        ApiError apiError = new ApiError(new Date(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                notFoundException.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(apiError, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> handleForbiddenException(ForbiddenException forbiddenException, WebRequest request){
        ApiError apiError = new ApiError(new Date(),
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN,
                forbiddenException.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(apiError, HttpStatus.FORBIDDEN);
    }

}
