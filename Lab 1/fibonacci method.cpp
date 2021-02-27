#include <bits/stdc++.h>
using namespace std;

double f(double argument) {
    return log10(argument - 2) * log10(argument - 2) + log10(10 - argument) * log10(10 - argument) - pow(argument, 0.2);
}

vector<double> fibonacci;
int create(double start, double end, double epsilon) {
    fibonacci = vector<double>();
    double length = (end - start) / epsilon;
    fibonacci.push_back(1);
    fibonacci.push_back(1);
    for (int i = 2; i < length; i++) {
        fibonacci.push_back(fibonacci[i - 2] + fibonacci[i - 1]);
    }
    return fibonacci.size();
}

double minimal(double start, double end, double epsilon) {
    int n = create(start, end, epsilon);
    double x1 = fibonacci[n - 3] / fibonacci[n - 1] * (end - start) + start;
    double x2 = fibonacci[n - 2] / fibonacci[n - 1] * (end - start) + start;
    double f1 = f(x1), f2 = f(x2);
    for (int i = 4; i < n; i++) {
        if (f1 < f2) {
            end = x2;
            x2 = x1;
            f2 = f1;
            x1 = fibonacci[n - i] / fibonacci[n - i + 2] * (end - start) + start;
            f1 = f(x1);
        } else {
            start = x1;
            x1 = x2;
            f2 = f1;
            x2 = fibonacci[n - i + 1] / fibonacci[n - i + 2] * (end - start) + start;
            f2 = f(x2);
        }
    }
    return (start + end) / 2;
}

int main() {
    double start = 6, end = 9.9;
    double epsilon;
    // epsilon must be more than 0.001
    cout << "Please, enter epsilon:" << "\n";
    cin >> epsilon;
    double answer = minimal(start, end, epsilon);
    cout << "Answer is x = " << answer << ", f(x) = " << f(answer) << "\n";
}