/**
 * Copyright (c) 2012, s3auth.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the s3auth.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.s3auth.hosts;

import com.jcabi.urn.URN;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URI;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * Single user.
 *
 * <p>Implementation must be immutable and thread-safe.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 0.0.1
 */
@User.Valid
public interface User {

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = User.Validator.class)
    @Documented
    public @interface Valid {
        /**
         * Message of the validation error.
         */
        String message() default "invalid user";
        /**
         * Groups.
         */
        Class<?>[] groups() default { };
        /**
         * Payload.
         */
        Class<? extends Payload>[] payload() default { };
    }

    /**
     * Validator of User.
     */
    class Validator implements ConstraintValidator<User.Valid, User> {
        /**
         * {@inheritDoc}
         */
        @Override
        public void initialize(final User.Valid annotation) {
            //nothing to do
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(final User user,
            final ConstraintValidatorContext ctx) {
            boolean isValid = true;
            if (!"facebook".equals(user.identity().nid())) {
                ctx.buildConstraintViolationWithTemplate("invalid NID of URN")
                    // @checkstyle MultipleStringLiterals (1 line)
                    .addNode("identity")
                    .addConstraintViolation();
                isValid = false;
            }
            if (!user.identity().nss().matches("\\d+")) {
                ctx.buildConstraintViolationWithTemplate("invalid NSS of URN")
                    .addNode("identity")
                    .addConstraintViolation();
                isValid = false;
            }
            return isValid;
        }
    }

    /**
     * Unique name of it.
     * @return Unique name as URN
     */
    URN identity();

    /**
     * Full name to display.
     * @return Full name
     */
    String name();

    /**
     * Photo.
     * @return URL of the image
     */
    URI photo();

}
