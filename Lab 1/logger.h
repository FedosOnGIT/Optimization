#pragma once

#include <memory>
#include <fstream>

using p_ostream = std::unique_ptr<std::ostream>;

class logger {
  p_ostream out;

  // Эта функция печатает в лог слова, разделённые табуляцией.
  // После последнего слова ничего напечатано не будет.
  void spec_print();

  template <typename Current>
  void spec_print(Current current);

  template <typename Current, typename... Rest>
  void spec_print(Current current, Rest... rest);

public:
  logger(std::ostream& out);
  explicit logger(std::string const& file);

  // Печатает в лог, добавляя в конец табуляцию.
  template <typename... Args>
  logger& print(Args... args);

  // Печатает в лог, добавляя в конец перевод строки.
  template <typename... Args>
  logger& println(Args... args);

  template <typename T>
  friend logger& operator<<(logger&, T const&);
};
