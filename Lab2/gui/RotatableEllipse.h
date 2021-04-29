#pragma once

#include <qcustomplot.h>
#include <Second.h>
#include <memory>
#include <iostream>
#include <cmath>

struct RotatableEllipse : public QCPItemEllipse {
    using QCPItemEllipse::QCPItemEllipse;

    RotatableEllipse(QCustomPlot* qcustomPlot, QTransform transform)
        : QCPItemEllipse(qcustomPlot), customPlot(qcustomPlot), transform(transform)
    {
        x_size = customPlot->xAxis->range().size();
        y_size = customPlot->yAxis->range().size();
    }

    void draw(QCPPainter* painter) override {
        QPointF p1 = topLeft->pixelPosition();
        QPointF p2 = bottomRight->pixelPosition();
        if (p1.toPoint() == p2.toPoint()) {
            return;
        }
        QRectF ellipseRect = QRectF(p1, p2).normalized();
        QRect clip = clipRect().adjusted(-mainPen().widthF(), -mainPen().widthF(), mainPen().widthF(), mainPen().widthF());
            if (ellipseRect.intersects(clip)) // only draw if bounding rect of ellipse is visible in cliprect
            {
              painter->setPen(mainPen());
              painter->setBrush(mainBrush());
          #ifdef __EXCEPTIONS
              try // drawEllipse sometimes throws exceptions if ellipse is too big
              {
          #endif
                painter->save();

                QPointF center = ellipseRect.center();

                painter->translate(center.x(), center.y());
                QRect newEllipseRect(-ellipseRect.width() / 2, -ellipseRect.height() / 2, ellipseRect.width(), ellipseRect.height());

                painter->setTransform(transform, true);

                painter->drawEllipse(newEllipseRect);

                painter->restore();
          #ifdef __EXCEPTIONS
              } catch (...)
              {
                qDebug() << Q_FUNC_INFO << "Item too large for memory, setting invisible";
                setVisible(false);
              }
          #endif
            }
    }

private:
    void updateTransform();

private:
    QCustomPlot* customPlot;
    double x_size, y_size;
    QTransform transform;
};
