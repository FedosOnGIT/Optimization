#pragma once

#include <qcustomplot.h>
#include <mainwindow.h>
#include <memory>
#include <iostream>
#include <cmath>
#include "SecondOrderCurve.h"

struct RotatableEllipse : public QCPItemEllipse {
    using QCPItemEllipse::QCPItemEllipse;

    RotatableEllipse(QCustomPlot* qcustomPlot, SecondOrderCurve curve)
        : QCPItemEllipse(qcustomPlot), customPlot(qcustomPlot), curve(curve)
    {
        x_size = customPlot->xAxis->range().size();
        y_size = customPlot->yAxis->range().size();
    }

    void draw(QCPPainter* painter) override {



        double cur_x_size = customPlot->xAxis->range().size();
        double c_x = cur_x_size / x_size;
        double cur_y_size = customPlot->yAxis->range().size();
        double c_y = cur_y_size / y_size;

        painter->save();

        QTransform painter_transform = painter->transform();

        painter->setTransform(QTransform( c_x, 0, 0, c_y, 0, 0), false);

        painter->setTransform(painter_transform, true);

        SecondOrderCurve cur_curve = curve;
        cur_curve.set_a11(cur_curve.get_a11() / (c_x * c_x));
        cur_curve.set_a22(cur_curve.get_a22() / (c_y * c_y));
        cur_curve.set_a12(cur_curve.get_a12() / (c_x * c_y));
        cur_curve.set_a1(cur_curve.get_a1() / c_x);
        cur_curve.set_a2(cur_curve.get_a2() / c_y);

        std::optional<QTransform> transform = cur_curve.getTransformToCanonicalForm();
        std::optional<QRectF> curEllipseRect = cur_curve.getEllipseData();
        if (!curEllipseRect.has_value()) {
            return;
        }
        topLeft->setCoords(curEllipseRect->topLeft().x() + transform->dx(), curEllipseRect->topLeft().y() + transform->dy());
        bottomRight->setCoords(curEllipseRect->bottomRight().x() + transform->dx(), curEllipseRect->bottomRight().y() + transform->dy());

        QPointF p1 = topLeft->pixelPosition();
        QPointF p2 = bottomRight->pixelPosition();
        if (p1.toPoint() == p2.toPoint()) {
            return;
        }
        QRectF ellipseRect = QRectF(p1, p2).normalized();

        painter->setPen(mainPen());
        painter->setBrush(mainBrush());
          #ifdef __EXCEPTIONS
              try // drawEllipse sometimes throws exceptions if ellipse is too big
              {
          #endif



            double cur_x_size = customPlot->xAxis->range().size();
            double c_x = cur_x_size / x_size;
            double cur_y_size = customPlot->yAxis->range().size();
            double c_y = cur_y_size / y_size;

            double cosA = transform->m11();
            double sinA = transform->m21();
            double tgA = sinA / cosA;

            double cosB = c_x / (sqrt(c_x * c_x + tgA * tgA * c_y * c_y));
            double sinB = sqrt(1 - cosB * cosB) * (sinA > 0 ? -1 : 1);

            QTransform cur_transform = QTransform(cosB, sinB, -sinB, cosB, 0, 0);

            QPointF center = ellipseRect.center();

            painter->translate(center.x(), center.y());
            QRect newEllipseRect(-ellipseRect.width() / 2, -ellipseRect.height() / 2, ellipseRect.width(), ellipseRect.height());

            painter->setTransform(cur_transform, true);

            painter->drawEllipse(newEllipseRect);



//            if (labelCos != nullptr) {
//                labelCos->setText(QString("x_scale = ") + QString::fromStdString(std::to_string(c_x)) + QString(" cosB = ") + QString::fromStdString(std::to_string(cosB)));
//            }
//            if (labelSin != nullptr) {
//                labelSin->setText(QString("y_scale = ") + QString::fromStdString(std::to_string(c_y)) +  QString(" sinB = ") + QString::fromStdString(std::to_string(sinB)));
//            }
          #ifdef __EXCEPTIONS
              } catch (...)
              {
                qDebug() << Q_FUNC_INFO << "Item too large for memory, setting invisible";
                setVisible(false);
              }
          #endif
        painter->restore();
    }

private:
    void updateTransform();

private:
    QCustomPlot* customPlot;
    double x_size, y_size;
    SecondOrderCurve curve;
};
