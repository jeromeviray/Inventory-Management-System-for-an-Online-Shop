package com.project.inventory.exception;

import com.project.inventory.exception.account.AccountException;
import com.project.inventory.exception.account.AccountNotFoundException;
import com.project.inventory.exception.cart.CartNotFound;
import com.project.inventory.exception.cartItem.CartItemNotFoundException;
import com.project.inventory.exception.cartItem.CartItemNotValidException;
import com.project.inventory.exception.customer.CustomerAddressNotFoundException;
import com.project.inventory.exception.errorDetails.ErrorDetails;
import com.project.inventory.exception.order.OrderItemNotFoundException;
import com.project.inventory.exception.order.ShoppingOrderInvalidException;
import com.project.inventory.exception.paymentMethod.PaymentMethodNotFoundException;
import com.project.inventory.exception.product.ProductNotFound;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@ControllerAdvice
public class CustomGlobalException extends ResponseEntityExceptionHandler {

    // handle those specific exceptions
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> handleAccountNotFoundException(AccountNotFoundException accountNotFoundException,
                                                            WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND, new Date(), accountNotFoundException.getMessage(), request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AccountException.class)
    public ResponseEntity<?> handleAccountException(AccountException AccountException,
                                                            WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, new Date(), AccountException.getMessage(), request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CartNotFound.class)
    public ResponseEntity<?> handleCartNotFoundException(CartNotFound cartNotFound,
                                                            WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND, new Date(), cartNotFound.getMessage(), request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<?> handleCartItemNotFoundException(CartItemNotFoundException cartItemNotFoundException,
                                                            WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND, new Date(), cartItemNotFoundException.getMessage(), request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartItemNotValidException.class)
    public ResponseEntity<?> handleCartItemNotValidException(CartItemNotValidException cartItemNotValidException,
                                                             WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR, new Date(), cartItemNotValidException.getMessage(), request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomerAddressNotFoundException.class)
    public ResponseEntity<?> handleCustomerAddressNotFoundException(CustomerAddressNotFoundException customerAddressNotFoundException,
                                                            WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND, new Date(), customerAddressNotFoundException.getMessage(), request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaymentMethodNotFoundException.class)
    public ResponseEntity<?> handlePaymentMethodNotFoundException(PaymentMethodNotFoundException paymentMethodNotFoundException,
                                                                  WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND, new Date(), paymentMethodNotFoundException.getMessage(), request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<?> handleProductNotFoundException(ProductNotFound productNotFound,
                                                            WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND, new Date(), productNotFound.getMessage(), request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ShoppingOrderInvalidException.class)
    public ResponseEntity<?> handleShoppingOrderInvalidException(ShoppingOrderInvalidException shoppingOrderInvalidException,
                                                                 WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR, new Date(), shoppingOrderInvalidException.getMessage(), request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OrderItemNotFoundException.class)
    public ResponseEntity<?> handleOrderItemNotFoundException(OrderItemNotFoundException orderItemNotFoundException,
                                                              WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND,
                new Date(),
                orderItemNotFoundException.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<?> handleInvalidException(InvalidException invalidException,
                                                              WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND,
                new Date(),
                invalidException.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // handle global exceptions

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalExceptions(Exception exception,
                                                                 WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR, new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException notFoundException,
                                                    WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND, new Date(), notFoundException.getMessage(), request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> handleForbiddenException(ForbiddenException forbiddenException,
                                                     WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.FORBIDDEN, new Date(), forbiddenException.getMessage(), request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.FORBIDDEN);
    }

}
