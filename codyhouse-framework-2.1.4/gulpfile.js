var gulp = require('gulp');
var sass = require('gulp-sass');
var sassGlob = require('gulp-sass-glob');
var browserSync = require('browser-sync').create();
var postcss = require('gulp-postcss');
var autoprefixer = require('autoprefixer');
var cssvariables = require('postcss-css-variables');
var calc = require('postcss-calc');
var concat = require('gulp-concat');
var rename = require('gulp-rename');
var uglify = require('gulp-uglify');

// js file paths
var utilJsPath = 'main/assets/js'; // util.js path - you may need to update this if including the framework as external node module
var componentsJsPath = 'main/assets/js/components/*.js'; // component js files
var scriptsJsPath = '/Users/dutiesheng/Code/SpringBoot/shop/src/main/resources/static/js'; //folder for final scripts.js/scripts.min.js files

// css file paths
var cssFolder = '/Users/dutiesheng/Code/SpringBoot/shop/src/main/resources/static/css'; // folder for final style.css/style-custom-prop-fallbac.css files
var scssFilesPath = 'main/assets/css/**/*.scss'; // scss files to watch

function reload(done) {
  browserSync.reload();
  done();
}

gulp.task('sass', function () {
  return gulp.src(scssFilesPath)
    .pipe(sassGlob())
    .pipe(sass({ outputStyle: 'compressed' }).on('error', sass.logError))
    .pipe(postcss([autoprefixer()]))
    .pipe(gulp.dest(cssFolder))
    .pipe(browserSync.reload({
      stream: true
    }))
    .pipe(rename('style-fallback.css'))
    .pipe(postcss([cssvariables(), calc()]))
    .pipe(gulp.dest(cssFolder));
});

gulp.task('scripts', function () {
  return gulp.src([utilJsPath + '/util.js', componentsJsPath])
    .pipe(concat('scripts.js'))
    .pipe(gulp.dest(scriptsJsPath))
    .pipe(browserSync.reload({
      stream: true
    }))
    .pipe(rename('scripts.min.js'))
    .pipe(uglify())
    .pipe(gulp.dest(scriptsJsPath))
    .pipe(browserSync.reload({
      stream: true
    }));
});

gulp.task('browserSync', gulp.series(function (done) {
  browserSync.init({
    server: {
      baseDir: '/Users/dutiesheng/Code/SpringBoot/shop/src/main/resources'
    },
    notify: false
  })
  done();
}));

gulp.task('watch', gulp.series(['browserSync', 'sass', 'scripts'], function () {
  gulp.watch('/Users/dutiesheng/Code/SpringBoot/shop/src/main/resources/templates/**/*.html', gulp.series(reload));
  gulp.watch('main/assets/css/**/*.scss', gulp.series(['sass']));
  gulp.watch(componentsJsPath, gulp.series(['scripts']));
}));
