/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payment;

/**
 *
 * @author MTH
 */
public enum PaymentCardType {
    
    CREDIT_CARD {
        @Override
        public String toString() {
            return "CREDIT_CARD";
        }
    },
    
    DEBIT_CARD {
        @Override
        public String toString() {
            return "DEBIT_CARD";
        }
    }
    
}
