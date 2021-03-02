#pragma once

#include "MinMethod.h"

class BrentMethod : MinMethod {
private:
    double const FACTOR = (3 - sqrt(5)) / 2;

public:
    using MinMethod::MinMethod;

    static int sign(double argument) {
        return (argument > 0) - (argument < 0);
    }

    double min(func_t const& f, double l, double r, double eps) override {
        double x = l + FACTOR * (r - l), w = x, v = x;
        double fx = f(x), fw = fx, fv = fx, parabola_min;
        double d = r - l, e = d, g;

        // Печать в лог шапки таблицы
        println("№", "l", "r", "min", "f(min)", "2min", "f(2min)", "prev_2min", "f(prev_2min)",
                "parabola_min", "f(parabola_min)", "scale");

        unsigned int index = 0;

        // Условие выхода: расстояние от найденного минимума
        // до дальней границы отрезка меньше 2*eps
        while (std::abs(x - (l + r) / 2) + (r - l) / 2 >= 2*eps) {
            g = e, e = d;

            print(index, l, r, x, fx, w, fw, v, fv);
            bool parabola_accepted = false;
            if (fx != fw && fx != fv && fw != fv) {
                double x1, x2, x3, f1, f2, f3;
                if (x < w) {
                    if (x < v) {
                        x1 = x;
                        f1 = fx;
                        if (v < w) {
                            x2 = v; f2 = fv; x3 = w; f3 = fw;
                        } else {
                            x2 = w; f2 = fw; x3 = v; f3 = fv;
                        }
                    } else {
                        x1 = v; f1 = fv; x2 = x; f2 = fx; x3 = w; f3 = fw;
                    }
                } else {
                    if (w < v) {
                        x1 = w;
                        f1 = fw;
                        if (v < x) {
                            x2 = v; f2 = fv; x3 = x; f3 = fx;
                        } else {
                            x2 = x; f2 = fx; x3 = v; f3 = fv;
                        }
                    } else {
                        x1 = v; f1 = fv; x2 = w; f2 = fw; x3 = x; f3 = fx;
                    }
                }
                double a1 = (f2 - f1) / (x2 - x1);
                double a2 = ((f3 - f1) / (x3 - x1) - (f2 - f1) / (x2 - x1)) / (x3 - x2);
                parabola_min = 0.5 * (x1 + x2 - a1 / a2);
                if (l <= parabola_min && parabola_min <= r && std::abs(parabola_min - x) < g / 2) {
                    parabola_accepted = true;
                    if (parabola_min - l < eps || r - parabola_min < eps) {
                        parabola_min = x - sign(x - (l + r) / 2) * eps;
                    }
                }
            }
            if (!parabola_accepted) {
                if (x < (l + r) / 2) {
                    parabola_min = x + FACTOR * (r - x);
                    e = r - x;
                } else {
                    parabola_min = x - FACTOR * (x - l);
                    e = x - l;
                }
            }
            if (std::abs(parabola_min - x) < eps) {
                parabola_min = x + sign(parabola_min - x) * eps;
            }
            d = std::abs(parabola_min - x);
            double fu = f(parabola_min);
            println(parabola_min, fu, d/(r - l));
            if (fu <= fx) {
                if (parabola_min >= x) {
                    l = x;
                } else {
                    r = x;
                }
                v = w; w = x; x = parabola_min; fv = fw; fw = fx; fx = fu;
            } else {
                if (parabola_min >= x) {
                    r = parabola_min;
                } else {
                    l = parabola_min;
                }
                if (fu <= fw || w == x) {
                    parabola_min = w;
                    w = parabola_min;
                    fv = fw;
                    fw = fu;
                } else if (fu <= fv || v == x || v == w) {
                    v = parabola_min;
                    fv = fu;
                }
            }
            ++index;
        }
        return x;
    }
};